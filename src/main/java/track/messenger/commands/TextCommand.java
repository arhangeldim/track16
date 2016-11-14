package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.messages.Status;
import track.messenger.messages.StatusMessage;
import track.messenger.net.Session;

/**
 * Created by geoolekom on 14.11.16.
 */
public class TextCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            session.send(new StatusMessage(session.getUser(), Status.OK));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка авторизации.");
        }
    }
}
