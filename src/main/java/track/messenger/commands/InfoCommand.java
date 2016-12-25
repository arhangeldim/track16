package track.messenger.commands;

import track.messenger.messages.InfoMessage;
import track.messenger.messages.InfoResultMessage;
import track.messenger.messages.Message;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;

public class InfoCommand implements Command {
    @Override
    public void execute(Session session, Message message)
            throws CommandException, IOException, ProtocolException {
        InfoMessage infoMessage = (InfoMessage) message;
        if (infoMessage.getUserId() == null) {
            if (!session.hasLogined()) {
                session.send(new InfoResultMessage(
                        null,
                        InfoResultMessage.Status.FAIL,
                        "You are not loged in")
                );
                return;
            }
            session.send(new InfoResultMessage(
                    new InfoResultMessage.Result(
                            session.getUser().
                    )
            ));
        }
    }
}
