package track.messenger.messages;

import track.messenger.User;

/**
 * Created by geoolekom on 14.11.16.
 */

public class StatusMessage extends Message {

    private Status status;
    private String respond = "";

    public StatusMessage() {
        super(null, Type.MSG_STATUS);
        status = Status.ERROR;
    }

    public StatusMessage(User user, Status status) {
        super(user, Type.MSG_STATUS);
        this.status = status;
    }

    public StatusMessage(User user, Status status, String respond) {
        super(user, Type.MSG_STATUS);
        this.status = status;
        this.respond = respond;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    @Override
    public String toString() {
        return status.toString() + " " + respond;
    }
}
