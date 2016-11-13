package track.messenger.messages;

import track.messenger.User;

/**
 * Created by geoolekom on 13.11.16.
 */
public class InfoResultMessage extends Message {

    private User requestedUser;

    public InfoResultMessage(User sender) {
        super(sender, Type.MSG_AUTHORIZED);
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
