package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.client.TextMessage;
import track.messenger.messages.server.StatusMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;

import java.io.IOException;

public class TextCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        TextMessage textMessage = (TextMessage) message;
        MessageStore messageStore = (MessageStore) storeFactory.get(Type.MESSAGE_STORE);
        messageStore.addMessage(textMessage);
        session.send(StatusMessage.OKMessage);
    }
}
