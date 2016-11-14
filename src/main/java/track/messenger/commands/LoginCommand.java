package track.messenger.commands;

import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.LoginMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;

/**
 * Created by geoolekom on 14.11.16.
 */
public class LoginCommand implements Command {
    @Override
    public void execute(Session session, Message message) throws CommandException {
        LoginMessage msg = (LoginMessage) message;
        session.auth(msg.getUsername(), msg.getPassword());
        try {
            session.send(new InfoResultMessage(session.getUser()));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка авторизации.");
        }
    }
}
