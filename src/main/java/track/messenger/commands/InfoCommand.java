package track.messenger.commands;

import track.messenger.Main;
import track.messenger.messages.InfoMessage;
import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.store.UserStore;

/**
 * Created by geoolekom on 14.11.16.
 */
public class InfoCommand implements Command {

    private UserStore users;

    public InfoCommand(UserStore users) {
        this.users = users;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        InfoMessage msg = (InfoMessage) message;
        try {
            session.send(new InfoResultMessage(session.getUser(), users.getUser(msg.getRequestedUser())));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка запроса.");
        }
    }
}
