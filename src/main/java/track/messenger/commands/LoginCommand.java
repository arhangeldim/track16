package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.UserStore;

import java.security.MessageDigest;

/**
 * Created by geoolekom on 14.11.16.
 */
public class LoginCommand implements Command {

    private UserStore users;

    public LoginCommand(UserStore users) {
        this.users = users;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {

        LoginMessage msg = (LoginMessage) message;
        User user = users.getUser(msg.getUsername());
        try {
            if (user != null && checkPassword(user, msg.getPassword())) {
                session.setUser(user);
                session.send(new StatusMessage(user, Status.AUTHORIZED, user.getUsername()));
                session.send(new InfoResultMessage(user));
            } else {
                session.send(new StatusMessage(user, Status.AUTHORIZATION_ERROR));
            }
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка авторизации. " + e.toString());
        }
    }

    private boolean checkPassword(User user, String password) throws Exception {
        MessageDigest hasher = MessageDigest.getInstance(users.getHashAlgorithm());
        hasher.update(password.getBytes());
        String encryptedPassword = new String(hasher.digest());
        if (encryptedPassword.equals(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
