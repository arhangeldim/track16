package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.LoginMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

/**
 * Created by ksushar on 1/26/17.
 */
public class LoginCommand implements Command {
    @Override
    public void execute(Session session, Message message, MessageStore messageStore, UserStore userStore) {
        LoginMessage loginMessage = (LoginMessage) message;
        User user = userStore.getUser(loginMessage.getLogin());
        if (user == null) {
            userStore.addUser(new User(loginMessage.getLogin(), loginMessage.getPassword()));
            user = userStore.getUser(loginMessage.getLogin());
        }
        if (!user.checkPassword(loginMessage.getPassword())) {
            StatusMessage statusMessage = new StatusMessage("FAIL", "Incorrect password");
            session.send(statusMessage);
            return;
        }
        session.setUser(user);
        StatusMessage statusMessage = new StatusMessage("OK");
        session.send(statusMessage);
    }
}
