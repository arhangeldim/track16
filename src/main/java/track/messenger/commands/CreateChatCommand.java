package track.messenger.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.messages.ChatCreate;
import track.messenger.messages.Message;
import track.messenger.messages.StatusMessage;
import track.messenger.messages.Type;
import track.messenger.net.MessengerServer;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;
import track.messenger.store.UserFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by asus on 02.12.16.
 */
public class CreateChatCommand implements Command {
    private final MessengerServer server;
    static Logger log = LoggerFactory.getLogger(CreateChatCommand.class);
    public CreateChatCommand(MessengerServer server) {
        this.server = server;
    }

    @Override
    public void execute(Session session, Message message) throws CommandException {
        log.info("Createe");
        try {
            if (session.getUser() == null) {
                StatusMessage errorMessage = new StatusMessage();
                errorMessage.setStatus("Ony authorised person can create chat");
                errorMessage.setType(Type.MSG_STATUS);
                session.send(errorMessage);
                return;
            }

            UserFactory userFactory = session.getUserFactory();
            StatusMessage response = new StatusMessage();

            String[] participants = ((ChatCreate) message).getUsersIds();
            log.info("participants: {}", (Object) participants);
            if (participants != null) {
                if (participants.length == 1) {
                    response.setStatus(String.format(
                                    "Uncorrect input"));
                    response.setType(Type.MSG_STATUS);
                    session.send(response);

                }
                else {
                    Chat chat = userFactory.createChat(session.getUser().getId(), participants, server.getnumchat());

                    server.chatincr();

                    response.setStatus(String.format("Chat created. Chat id: %d", chat.getId()));
                    response.setType(Type.MSG_STATUS);
                    session.send(response);
                    return;
                }
            }


        } catch ( ProtocolException | IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
