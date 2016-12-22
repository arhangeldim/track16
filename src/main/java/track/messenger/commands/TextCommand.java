package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.TextMessage;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 02.12.16.
 */
public class TextCommand implements Command {
    static Logger log = LoggerFactory.getLogger(TextCommand.class);

    private MessengerServer server;

    public TextCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException, IOException, ProtocolException, SQLException {
        StatusMessage errorMessage = new StatusMessage();
        if (session.getUser() == null) {
            errorMessage.setStatus("Only authorised person can send messages");
            session.send(errorMessage);
            return;
        }

        UserFactory userFactory = session.getUserFactory();

        TextMessage textMessage = (TextMessage) message;
        textMessage.setSenderId(session.getUser().getId());

        Chat chat = userFactory.getChatById(textMessage.getChatId());
        userFactory.addMessage(chat.getId(), textMessage);
        StatusMessage response = new StatusMessage();
        response.setStatus(String.format("User %s wrote to chat %d: %s",
                session.getUser().getName(), chat.getId(), textMessage.getText()));
        response.setSenderId(textMessage.getSenderId());
        log.info("sessions: {}", server.getSessions());

        for (Long chatUsersId : chat.getParticipantIds()) {
            for (Session s : server.getSessions()) {
                if (s.getUser() != null && chatUsersId.equals(s.getUser().getId())) {
                    s.send(response);
                }
            }

        }

    }
}
