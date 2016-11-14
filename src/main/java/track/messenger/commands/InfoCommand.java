package track.messenger.commands;

import track.messenger.Main;
import track.messenger.messages.InfoMessage;
import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;

/**
 * Created by geoolekom on 14.11.16.
 */
public class InfoCommand implements Command {

    @Override
    public void execute(Session session, Message message) throws CommandException {
        InfoMessage msg = (InfoMessage) message;
        try {
            session.send(new InfoResultMessage(session.getUser(), Main.users.getUser(msg.getRequestedUser())));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка запроса.");
        }
    }
}
