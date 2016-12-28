package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.client.InfoMessage;
import track.messenger.messages.server.InfoResultMessage;
import track.messenger.messages.server.InfoResultMessage.InfoResult;
import track.messenger.messages.Message;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.StoreFactory;
import track.messenger.store.Type;
import track.messenger.store.UserStore;

import java.io.IOException;

public class InfoCommand implements Command {
    @Override
    public void execute(Session session, Message message, StoreFactory storeFactory)
            throws CommandException, IOException, ProtocolException {
        if (!session.hasLogined()) {
            session.send(new InfoResultMessage(
                    null,
                    InfoResultMessage.Status.FAIL,
                    "You are not loged in")
            );
            return;
        }

        InfoMessage infoMessage = (InfoMessage) message;
        User infoUser;
        if (infoMessage.getUserId() == null) {
            infoUser = session.getUser();
        } else {
            UserStore userStore = (UserStore) storeFactory.get(Type.USER_STORE);
            infoUser = userStore.getUserById(infoMessage.getUserId());
        }
        session.send(new InfoResultMessage(
                        new InfoResult(
                                infoUser.getId(),
                                infoUser.getLogin()
                        ),
                        InfoResultMessage.Status.OK,
                        null
                )
        );
    }

    public static void main(String... strings) throws ProtocolException, IOException, CommandException {
        Session session = new Session();
        User user = new User(1L, "aaa", "aaa");
        session.setUser(user);
        InfoMessage infoMessage = new InfoMessage();
        Command infoCommand = new InfoCommand();
        infoCommand.execute(session, infoMessage, null);
    }
}
