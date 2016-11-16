package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.UserStore;

/**
 * Created by geoolekom on 16.11.16.
 */
public class RegisterCommand implements Command {

    UserStore users;

    public RegisterCommand(UserStore users) {
        this.users = users;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        RegisterMessage msg = (RegisterMessage) message;
        User user = new User(msg.getUsername(), msg.getPassword());
        try {
            users.saveUser(user);
            session.setUser(user);
            session.send(new StatusMessage(user, Status.AUTHORIZED, user.getUsername()));
            session.send(new InfoResultMessage(user));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка регистрации. " + e.toString());
        }
    }
}
