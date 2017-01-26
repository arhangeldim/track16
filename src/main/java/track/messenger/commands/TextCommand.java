package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.client.TextMessage;
import track.messenger.messages.server.ResultMessage;
import track.messenger.messages.server.StatusMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;
import track.messenger.store.UserStore;

import java.io.IOException;

public class TextCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (!session.hasLogined()) {
            session.send(new StatusMessage(
                    ResultMessage.Status.FAIL,
                    "You are not loged in")
            );
            return;
        }
        TextMessage textMessage = (TextMessage) message;
        MessageStore messageStore = (MessageStore) storeFactory.get(Type.MESSAGE_STORE);
        if (messageStore.getChatById(textMessage.getChatId()) == null) {
            session.send(new StatusMessage(
                    ResultMessage.Status.FAIL,
                    "Chat doesn't exist")
            );
            return;
        }
        if (!messageStore.getChatIdsByUserId(session.getUser().getId()).contains(textMessage.getChatId())) {
            session.send(new StatusMessage(
                    ResultMessage.Status.FAIL,
                    "You are not participant of this chat")
            );
            return;
        }
        messageStore.addTextMessage(textMessage);
        session.send(StatusMessage.OKMessage);
    }
}
