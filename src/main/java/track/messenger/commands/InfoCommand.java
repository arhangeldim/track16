package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.MessengerServer;
import track.messenger.net.Session;

/**
 * Created by asus on 02.12.16.
 */
public class InfoCommand implements Command {
    private final MessengerServer server;

    public InfoCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {

    }
}
