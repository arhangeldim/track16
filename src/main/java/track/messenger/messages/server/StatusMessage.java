package track.messenger.messages.server;

import track.messenger.messages.Type;
import track.messenger.messages.server.ResultMessage;

public class StatusMessage extends ResultMessage {

    public static final StatusMessage OKMessage = new StatusMessage(Status.OK, null);

    public StatusMessage() {
        super(Type.MSG_STATUS);
    }

    public StatusMessage(Status status, String errorMessage) {
        super(Type.MSG_STATUS);
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
