package track.messenger.messages;

import track.messenger.store.dao.User;

import java.io.Serializable;

/**
 * Created by geoolekom on 13.11.16.
 */
public class InfoResultMessage extends Message implements Serializable {

    private User requestedUser;

    protected InfoResultMessage() {
        super(null, Type.MSG_USER_INFO);
    }

    public InfoResultMessage(User sender) {
        super(sender, Type.MSG_SELF_INFO);
        this.requestedUser = sender;
    }

    public InfoResultMessage(User sender, User requestedUser) {
        super(sender, Type.MSG_USER_INFO);
        this.requestedUser = requestedUser;
    }

    public User getRequestedUser() {
        return requestedUser;
    }
}
