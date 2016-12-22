package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.Registration;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.Type;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 15.12.16.
 */
public class RegistrationCommand implements Command {
    private MessengerServer server;
    static Logger log = LoggerFactory.getLogger(RegistrationCommand.class);
    public RegistrationCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException, SQLException, IOException, ProtocolException, ClassNotFoundException {
        Registration registration = (Registration) message;
        User user = new User();
        user.setName(registration.getUsername());
        user.setPassword(registration.getPassword());
        user.setId(server.getnumlogin());
        UserFactory userFactory = session.getUserFactory();
        log.info("Name: {}", user.getName());
        StatusMessage result = new StatusMessage();
        result.setType(Type.MSG_STATUS);
        if (userFactory.addUser(user, server.getnumlogin()) == null) {
            result.setStatus("This login exists");
        }
        else {
            result.setStatus(String.format("Registration successful!: userid: %d, name: %s", user.getId(), user.getName()));
            server.loginincr();
        }
        try {
            session.send(result);
        } catch (IOException | ProtocolException e) {
            e.printStackTrace();
        }
    }
}
