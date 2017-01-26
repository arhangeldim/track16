package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.client.ChatHistMessage;
import track.messenger.messages.server.ChatHistResultMessage;
import track.messenger.messages.server.ChatHistResultMessage.ChatHistResult;
import track.messenger.messages.server.ResultMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;

import java.io.IOException;

public class ChatHistCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (! session.hasLogined()) {
            session.send(new ChatHistResultMessage(
                    null,
                    ResultMessage.Status.FAIL,
                    "You need to login")
            );
            return;
        }
        ChatHistMessage chatHistMessage = (ChatHistMessage) message;
        MessageStore messageStore = (MessageStore) storeFactory.get(Type.MESSAGE_STORE);
        if(messageStore
                .getChatById(chatHistMessage.getChatId())
                .getUsers()
                .stream()
                .mapToLong(User::getId)
                .noneMatch(value -> value == session.getUser().getId())) {
            session.send(new ChatHistResultMessage(
                    null,
                    ResultMessage.Status.FAIL,
                    "You are not a participant of this chat")
            );
            return;
        }
        session.send(new ChatHistResultMessage(
                new ChatHistResult(messageStore.getMessagesFromChat(chatHistMessage.getChatId())),
                ResultMessage.Status.OK,
                null)
        );
    }
}
