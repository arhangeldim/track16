package track.messenger.messages;

import track.messenger.store.dao.User;

import java.util.List;

/**
 * Created by geoolekom on 17.11.16.
 */
public class ChatListResultMessage extends Message {
    private List<Integer> chatIds;

    public List<Integer> getChatIds() {
        return chatIds;
    }

    public ChatListResultMessage() {
        super(null, Type.MSG_CHAT_LIST_RESULT);
    }

    public ChatListResultMessage(User sender, List<Integer> chatIds) throws InstantiationException {
        super(sender, Type.MSG_CHAT_LIST_RESULT);
        this.chatIds = chatIds;
    }
}
