package track.messenger.messages;

import track.messenger.User;

/**
 * Created by geoolekom on 14.11.16.
 */

public class StatusMessage extends Message {

    private Status status;

    public StatusMessage() {
        super(null, Type.MSG_STATUS);
        status = Status.OK;
    }

    public StatusMessage(User user, Status status) {
        super(user, Type.MSG_STATUS);
        this.status = status;
    }

    @Override
    public String toString() {
        return status.toString();
    }
}
