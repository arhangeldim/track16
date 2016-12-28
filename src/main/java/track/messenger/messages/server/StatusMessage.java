package track.messenger.messages.server;

import track.messenger.messages.Type;
import track.messenger.messages.server.ResultMessage;

public class StatusMessage extends ResultMessage {
    public StatusMessage(Status status, String errorMessage) {
        super(Type.MSG_STATUS);
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
