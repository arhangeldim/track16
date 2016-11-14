package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.Session;

/**
 * Created by geoolekom on 14.11.16.
 */
public interface Command {
    void execute(Session session, Message message) throws CommandException;
}
