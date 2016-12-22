package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.LoginMessage;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.Type;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 30.11.16.
 */
public class LoginCommand implements Command {

    private MessengerServer server;
    static Logger log = LoggerFactory.getLogger(LoginCommand.class);
    public LoginCommand(MessengerServer server) {
        this.server = server;
    }


    @Override
    public void execute(Session session, Message message) throws CommandException, SQLException {
        LoginMessage loginMessage = (LoginMessage) message;

        String userName = loginMessage.getUsername();
        String password = loginMessage.getPassword();

        UserFactory userFactory = session.getUserFactory();
        StatusMessage result = new StatusMessage();
        result.setType(Type.MSG_STATUS);
        if (!userFactory.isUserExist(userName)) {
            log.info("userName: {}", userName);
            result.setStatus("User not found");
            try {
                session.send(result);
            } catch (IOException | ProtocolException e) {
                e.printStackTrace();
            }
            return;
        }
        User user = userFactory.getUser(userName, password);
        if (user == null) {
            result.setStatus("Login or password is incorrect");
        } else {
            session.setUser(user);
            result.setStatus(String.format("Success: userid: %d, name: %s", user.getId(), user.getName()));
        }

        try {
            session.send(result);
        } catch (IOException | ProtocolException e) {
            e.printStackTrace();
        }
    }
}


