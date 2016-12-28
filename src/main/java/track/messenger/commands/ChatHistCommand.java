package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.server.ChatHistResultMessage;
import track.messenger.messages.server.ResultMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StoreFactory;

import java.io.IOException;

public class ChatHistCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (! session.hasLogined()) {
            session.send(new ChatHistResultMessage(
                    null,
                    ResultMessage.Status.FAIL,
                    "you need to login")
            );
            return;
        }
        // TODO continue
    }
}
