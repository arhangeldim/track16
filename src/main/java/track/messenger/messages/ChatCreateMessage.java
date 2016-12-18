package track.messenger.messages;

import java.util.List;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class ChatCreateMessage extends Message {

    private List<Long> userIds;

    public ChatCreateMessage() {
        super(Type.MSG_CHAT_CREATE);
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
