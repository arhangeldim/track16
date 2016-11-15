package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.Status;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;
import track.messenger.net.Session;

/**
 * Created by geoolekom on 14.11.16.
 */
public class TextCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        try {
            User user = session.getUser();
            TextMessage msg = (TextMessage) message;
            if (user != null) {
                session.send(new StatusMessage(user, Status.MESSAGE_DELIEVERED));
            } else {
                session.send(new StatusMessage(user, Status.NOT_AUTHORIZED));
            }

        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка авторизации.");
        }
    }
}
