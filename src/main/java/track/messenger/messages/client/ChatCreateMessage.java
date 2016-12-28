package track.messenger.messages.client;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

import java.util.List;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class ChatCreateMessage extends Message {

    private List<Long> userIds;

    public ChatCreateMessage(List<Long> userIds) {
        super(Type.MSG_CHAT_CREATE);
        this.userIds = userIds;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
