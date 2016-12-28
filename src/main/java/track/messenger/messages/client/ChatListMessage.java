package track.messenger.messages.client;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

public class ChatListMessage extends Message {
    public ChatListMessage() {
        super(Type.MSG_CHAT_LIST);
    }
}
