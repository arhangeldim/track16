package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.Message;
import track.messenger.messages.Type;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 02.12.16.
 */
public class CommandExecutor {
    static Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    private Map<Type, Command> commands;

    public CommandExecutor() {
        commands = new HashMap<>();
    }

    public CommandExecutor addCommand(Type type, Command command) {
        commands.put(type, command);
        log.info("addCommand type: {}", type);
        log.info("addCommand command: {}", command);
        log.info(String.valueOf(commands.containsKey(Type.MSG_LOGIN)));
        return this;
    }

    public void handleMessage(Message message, Session session) throws CommandException, ProtocolException, SQLException, IOException, ClassNotFoundException {
        Type messageType = message.getType();
        log.info("handlemessage: {}", message);
        log.info("Type: {}", messageType);

        if (messageType == null) {
            log.info("null");
            throw new CommandException("Message type is null");
        } else if (commands.containsKey(messageType)) {
            log.info("func: {}", commands.get(messageType));
            commands.get(messageType).execute(session, message);
        } else {
            throw new CommandException("Undefined message type");
        }
    }
}
//запросы с базойданной в одном классе. В базах