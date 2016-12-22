package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 30.11.16.
 */
public interface Command {
    void execute(Session session, Message message) throws CommandException, SQLException, IOException, ProtocolException, ClassNotFoundException;
}
