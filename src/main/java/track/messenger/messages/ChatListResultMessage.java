package track.messenger.messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 21.11.16.
 */
public class ChatListResultMessage extends Message{
    private List<Long> chatIds;
    public ChatListResultMessage() {
        super(null);
        chatIds = new ArrayList<>();
    }
    public List<Long> getChatIds() {
        return chatIds;
    }

    public void setChatIds(List<Long> chatIds) {
        this.chatIds = chatIds;
    }
}
