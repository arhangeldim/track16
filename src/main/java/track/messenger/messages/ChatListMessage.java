package track.messenger.messages;

import track.messenger.store.dao.User;

/**
 * Created by geoolekom on 17.11.16.
 */
public class ChatListMessage extends Message {

    public ChatListMessage() {
        super(null, Type.MSG_CHAT_LIST);
    }

    public ChatListMessage(User sender) throws InstantiationException {
        super(sender,Type.MSG_CHAT_LIST);
        if (sender == null) {
            throw new InstantiationException("Вы не авторизованы. ");
        }
    }

}
