package track.messenger;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observables.BlockingObservable;
import track.container.Container;
import track.container.JsonConfigReader;
import track.messenger.messages.Type;
import track.messenger.messages.client.TextMessage;
import track.messenger.messages.server.*;
import track.messenger.net.JsonProtocol;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.teacher.client.InvalidInputExeption;
import track.messenger.teacher.client.MessengerClient;
import track.messenger.teacher.client.ReceiveCallback;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainTest {

    MessengerServer server;
    List<User> users = new ArrayList<>();
    User notLoggedUser;

    @Before
    public void initServer() {
        new Thread(() -> {
            Container container = new Container("src/main/resources/server.json", new JsonConfigReader());
            server = (MessengerServer) container.getById("messengerServer");
            server.setPort(19001);
            if (server == null) {
                System.out.println("Cannot initialize sever.");
                return;
            }
            server.clearState();
            server.runServer();
        }
        ).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initClients() throws IOException {
        users.add(new User(new MessengerClient()));
        users.add(new User(new MessengerClient()));
        users.add(new User(new MessengerClient()));
        notLoggedUser = new User(new MessengerClient());
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            user.login = "c" + Integer.toString(i+1);
            user.password = "c" + Integer.toString(i+1);
            user.client.setPort(19001);
            user.client.setHost("localhost");
            user.client.setProtocol(new JsonProtocol());
            user.client.initSocket();
            user.observable = Observable.create(new MessageReceiveOnSubscribe(user.client)).
                    toBlocking();
        }
        notLoggedUser.client.setPort(19001);
        notLoggedUser.client.setHost("localhost");
        notLoggedUser.client.setProtocol(new JsonProtocol());
        notLoggedUser.client.initSocket();
        notLoggedUser.observable = Observable.create(new MessageReceiveOnSubscribe(notLoggedUser.client)).
                toBlocking();

    }

    @Test
    public void testCommands() throws ProtocolException, InvalidInputExeption, IOException {
        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);
        ResultMessage msg;

        msg = user1.recieveMessageForInput("/info");
        assertTrue(msg.getType() == Type.MSG_INFO_RESULT);
        assertTrue(msg.status == ResultMessage.Status.FAIL);

        for (User user : users) {
            msg = user.recieveMessageForInput("/login " + user.login + " " + user.password);
            assertTrue(msg.getType() == Type.MSG_STATUS);
            assertTrue(msg.status == ResultMessage.Status.OK);
        }

        msg = notLoggedUser.recieveMessageForInput("/login " + user1.login + " ----");
        assertTrue(msg.getType() == Type.MSG_STATUS);
        assertTrue(msg.status == ResultMessage.Status.FAIL);

        for (User user : users) {
            msg = user.recieveMessageForInput("/info");
            assertTrue(msg.getType() == Type.MSG_INFO_RESULT);
            assertTrue(msg.status == ResultMessage.Status.OK);
            assertEquals(((InfoResultMessage) msg).result.userLogin, user.login);
            assertEquals (((InfoResultMessage) msg).result.userId, new Long(users.indexOf(user) + 1));
        }

        msg = user3.recieveMessageForInput("/info 1");
        assertTrue(msg.getType() == Type.MSG_INFO_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        assertEquals(((InfoResultMessage) msg).result.userLogin, user1.login);
        assertEquals(((InfoResultMessage) msg).result.userId, new Long(1));

        msg = user1.recieveMessageForInput("/chat_create 1 2 3");
        assertTrue(msg.getType() == Type.MSG_CHAT_CREATE_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        assertEquals(((ChatCreateResultMessage) msg).result.chatId, new Long(1));

        msg = user1.recieveMessageForInput("/chat_create 1 2");
        assertTrue(msg.getType() == Type.MSG_CHAT_CREATE_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        assertEquals(((ChatCreateResultMessage) msg).result.chatId, new Long(2));

        msg = user1.recieveMessageForInput("/chat_list");
        assertTrue(msg.getType() == Type.MSG_CHAT_LIST_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        assertEquals(((ChatListResultMessage) msg).result.chatList, Arrays.asList(1L, 2L));

        msg = user3.recieveMessageForInput("/chat_list");
        assertTrue(msg.getType() == Type.MSG_CHAT_LIST_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        assertEquals(((ChatListResultMessage) msg).result.chatList, Collections.singletonList(1L));

        msg = user1.recieveMessageForInput("/text 1 aa");
        assertTrue(msg.getType() == Type.MSG_STATUS);
        assertTrue(msg.status == ResultMessage.Status.OK);

        msg = user2.recieveMessageForInput("/text 1 bb");
        assertTrue(msg.getType() == Type.MSG_STATUS);
        assertTrue(msg.status == ResultMessage.Status.OK);

        msg = user3.recieveMessageForInput("/text 1 cc");
        assertTrue(msg.getType() == Type.MSG_STATUS);
        assertTrue(msg.status == ResultMessage.Status.OK);

        msg = user1.recieveMessageForInput("/text 1 dd");
        assertTrue(msg.getType() == Type.MSG_STATUS);
        assertTrue(msg.status == ResultMessage.Status.OK);

        msg = user1.recieveMessageForInput("/chat_history 1");
        assertTrue(msg.getType() == Type.MSG_CHAT_HIST_RESULT);
        assertTrue(msg.status == ResultMessage.Status.OK);
        List<TextMessage> chatHist = ((ChatHistResultMessage) msg).result.chatHist;
        assertEquals(chatHist.get(0).getChatId(), new Long(1));
        assertEquals(chatHist.get(0).getSenderId(), new Long(1));
        assertEquals(chatHist.get(0).getText(), "aa");
        assertEquals(chatHist.get(1).getChatId(), new Long(1));
        assertEquals(chatHist.get(1).getSenderId(), new Long(2));
        assertEquals(chatHist.get(1).getText(), "bb");
        assertEquals(chatHist.get(2).getChatId(), new Long(1));
        assertEquals(chatHist.get(2).getSenderId(), new Long(3));
        assertEquals(chatHist.get(2).getText(), "cc");
        assertEquals(chatHist.get(3).getChatId(), new Long(1));
        assertEquals(chatHist.get(3).getSenderId(), new Long(1));
        assertEquals(chatHist.get(3).getText(), "dd");
    }

    private class User {
        MessengerClient client;
        BlockingObservable<ResultMessage> observable;

        String login;
        String password;

        public User(MessengerClient client) {
            this.client = client;
        }

        ResultMessage recieveMessageForInput(String input)
                throws ProtocolException, InvalidInputExeption, IOException {
            client.processInput(input);
            return observable.first();
        }
    }
}
