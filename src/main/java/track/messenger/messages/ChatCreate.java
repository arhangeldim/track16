package track.messenger.messages;

import java.io.Serializable;

/**
 * Created by ivan on 21.11.16.
 */
public class ChatCreate extends Message implements Serializable {
    private String[] usersIds;

    public ChatCreate() {
        super(null);
    }

    public String[] getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(String[] userIds) {
        this.usersIds = userIds;
    }
}
