package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.store.StoreFactory;

public class LoginCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory) throws CommandException {

    }
}
