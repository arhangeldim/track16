package track.messenger.commands;

import track.messenger.Main;
import track.messenger.User;
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
        session.setUser(Main.users.loginUser(msg.getUsername(), msg.getPassword()));
        try {
            User user = session.getUser();
            if (user != null) {
                session.send(new InfoResultMessage(user));
            } else {
                throw new CommandException(this.getClass() + ": неправильный логин или пароль.");
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка авторизации.");
        }
    }
}
