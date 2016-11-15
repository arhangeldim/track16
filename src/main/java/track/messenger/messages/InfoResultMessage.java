package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;

/**
 * Created by geoolekom on 13.11.16.
 */
public class InfoResultMessage extends Message implements Serializable {

    private User requestedUser;

    protected InfoResultMessage() {
        super(null, Type.MSG_INFO_RESULT);
    }

    public InfoResultMessage(User sender) {
        super(sender, Type.MSG_INFO_RESULT);
        this.requestedUser = sender;
    }

    public InfoResultMessage(User sender, User requestedUser) {
        super(sender, Type.MSG_INFO_RESULT);
        this.requestedUser = requestedUser;
    }

    public User getRequestedUser() {
        return requestedUser;
    }
}
