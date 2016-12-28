package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.client.LoginMessage;
import track.messenger.messages.server.ResultMessage;
import track.messenger.messages.server.StatusMessage;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;
import track.messenger.store.UserStore;

import java.io.IOException;

public class LoginCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        LoginMessage loginMessage = (LoginMessage) message;
        UserStore userStore = (UserStore) storeFactory.get(Type.USER_STORE);
        User user = userStore.getUser(loginMessage.getLogin());
        if (user == null) {
            userStore.addUser(new User(
                    userStore.getFreeUserId(),
                    loginMessage.getLogin(),
                    loginMessage.getPassword())
            );
            session.send(StatusMessage.OKMessage);
            return;
        }
        if (! user.checkPassword(loginMessage.getPassword())) {
            session.send(new StatusMessage(ResultMessage.Status.FAIL, "Wrong password"));
            return;
        }
        session.setUser(user);
        session.send(StatusMessage.OKMessage);
    }
}
