package track.messenger.commands;

import track.messenger.messages.InfoMessage;
import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.security.CryptoSystem;
import track.messenger.store.StoreFactory;
import track.messenger.store.UserStore;
import track.messenger.store.dao.User;

/**
 * Created by geoolekom on 14.11.16.
 */
public class InfoCommand implements Command {

    @Override
    public void execute(Session session, Message message, CryptoSystem crypto, StoreFactory stores) throws CommandException {
        UserStore users = (UserStore) stores.get(User.class);
        InfoMessage msg = (InfoMessage) message;
        try {
            session.send(new InfoResultMessage(session.getUser(), users.getUser(msg.getRequestedUser())));
        } catch (Exception e) {
            throw new CommandException(this.getClass() + ": ошибка запроса. ");
        }
    }
}
