package track.messenger.commands;

import track.messenger.User;
import track.messenger.messages.*;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by asus on 02.12.16.
 */
public class ListChatCommand implements Command {
    private final MessengerServer server;

    public ListChatCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException, SQLException {
        try {
            ChatList chatListMessage = (ChatList)message;
            StatusMessage errorMessage = new StatusMessage();

            User user = session.getUser();
            if (user == null) {
                errorMessage.setStatus("Ony authorised person can see chats");
                errorMessage.setType(Type.MSG_STATUS);
                session.send(errorMessage);
                return;
            }

            UserFactory userFactory = session.getUserFactory();
            List<Long> chatIds = userFactory.getChatsByUserId(user.getId());
            if (chatIds == null) {
                errorMessage.setStatus("No chats for user");
                errorMessage.setType(Type.MSG_STATUS);
                session.send(errorMessage);
                return;
            }

            ChatListResultMessage chatListResultMessage = new ChatListResultMessage();
            chatListResultMessage.setType(Type.MSG_CHAT_LIST_RESULT);
            chatListResultMessage.setChatIds(chatIds);
            session.send(chatListResultMessage);

        } catch (IOException | ProtocolException e) {
            throw new CommandException(e);
        }

    }
}
