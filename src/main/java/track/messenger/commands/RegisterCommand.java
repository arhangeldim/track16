package track.messenger.commands;

import org.apache.commons.lang.RandomStringUtils;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.store.dao.User;
import track.messenger.messages.*;
import track.messenger.net.Session;
import track.messenger.store.UserStore;

/**
 * Created by geoolekom on 16.11.16.
 */
public class RegisterCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        UserStore users = (UserStore) stores.get(User.class);
        RegisterMessage msg = (RegisterMessage) message;
        try {
            if (users.getUser(msg.getUsername()) != null) {
                session.send(new StatusMessage(session.getUser(), Status.USER_EXISTS));
                throw new CommandException(this.getClass() + ": пользователь уже существует. ");
            }
            String salt = RandomStringUtils.randomAlphanumeric(crypto.getSaltLength()).toUpperCase();
            String encryptedPassword = crypto.encrypt(msg.getPassword(), salt);
            User user = new User(msg.getUsername(), encryptedPassword);

            session.setUser(user);
            session.send(new StatusMessage(user, Status.USER_CREATED, user.getUsername()));
            session.send(new InfoResultMessage(user));

            user.setSalt(salt);
            users.saveUser(user);
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка регистрации. " + e.toString());
        }
    }
}
