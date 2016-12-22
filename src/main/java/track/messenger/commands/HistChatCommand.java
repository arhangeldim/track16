package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.messages.*;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;
import track.messenger.store.Usermessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by asus on 02.12.16.
 */
public class HistChatCommand implements Command {
    static Logger log = LoggerFactory.getLogger(HistChatCommand.class);
    private final MessengerServer server;

    public HistChatCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException, IOException, ProtocolException, SQLException {
        ChatHistory chatHistory = (ChatHistory) message;
        UserFactory userFactory = session.getUserFactory();
        List<Usermessage> history = userFactory.getMessagesFromChat(chatHistory.getId());
        log.info("Usermessage: {}", history);
        if (history.isEmpty()) {
            StatusMessage response = new StatusMessage();
            response.setStatus(String.format("No messages in this chat"));
            response.setType(Type.MSG_STATUS);
            session.send(response);
        }
        else {
            ChatHistResultMessage chatHistResultMessage = new ChatHistResultMessage();
            chatHistResultMessage.setType(Type.MSG_CHAT_HIST_RESULT);
            chatHistResultMessage.setHistory(history);
            session.send(chatHistResultMessage);
        }
    }
}
