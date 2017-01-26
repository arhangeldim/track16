package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.server.ChatListResultMessage;
import track.messenger.messages.server.ChatListResultMessage.ChatListResult;
import track.messenger.messages.server.ResultMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;

import java.io.IOException;

public class ChatListCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (! session.hasLogined()) {
            session.send(new ChatListResultMessage(
                    null,
                    ResultMessage.Status.FAIL,
                    "you need to login")
            );
            return;
        }
        MessageStore messageStore = (MessageStore) storeFactory.get(Type.MESSAGE_STORE);
        session.send(new ChatListResultMessage(
                new ChatListResult(messageStore.getChatIdsByUserId(session.getUser().getId())),
                ResultMessage.Status.OK,
                null
        ));
    }
}
