package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.Status;
import track.messenger.messages.StatusMessage;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;

/**
 * Created by geoolekom on 14.11.16.
 */
public class QuitCommand implements Command {

    public QuitCommand() {}

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        try {
            session.send(new StatusMessage(session.getUser(), Status.GOODBYE));
            session.close();
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка выхода (бред какой-то). ");
        }
    }
}
