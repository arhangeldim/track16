package track.messenger.commands;

import track.messenger.store.dao.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.UserStore;

import java.security.MessageDigest;

/**
 * Created by geoolekom on 16.11.16.
 */
public class RegisterCommand implements Command {

    private UserStore users;
    private String hashAlgorithm;

    public RegisterCommand(UserStore users, String hashAlgorithm) {
        this.users = users;
        this.hashAlgorithm = hashAlgorithm;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        RegisterMessage msg = (RegisterMessage) message;
        User user = new User(msg.getUsername(), msg.getPassword());
        try {
            MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
            hasher.update(user.getPassword().getBytes());
            String encryptedPassword = new String(hasher.digest());
            user.setPassword(encryptedPassword);
            users.saveUser(user);
            session.setUser(user);
            session.send(new StatusMessage(user, Status.AUTHORIZED, user.getUsername()));
            session.send(new InfoResultMessage(user));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка регистрации. " + e.toString());
        }
    }
}
