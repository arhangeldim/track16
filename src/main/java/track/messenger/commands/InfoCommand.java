package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.InfoMessage;
import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.Session;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

public class InfoCommand implements Command {
    @Override
    public void execute(Session session, Message message, MessageStore messageStore, UserStore userStore) {
        if (session.getUser() == null) { //not logged in
            return;
        }
        InfoMessage infoMessage = (InfoMessage) message;
        Long userId = infoMessage.getUserId();
        if (userId == null) { //self information
            userId = session.getUser().getId();
        }
        User user = userStore.getUserById(userId);
        String info;
        if (user == null) {
            info = "'No such user.'";
        } else {
            info = "{" +
                    "login='" + user.getLogin() + "', " +
                    "password='" + user.getPassword() + "'" +
                    "}";
        }
        InfoResultMessage resultMessage = new InfoResultMessage(userId, info);
        session.send(resultMessage);
    }
}
