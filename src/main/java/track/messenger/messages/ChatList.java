package track.messenger.messages;

import java.io.Serializable;

/**
 * Created by ivan on 21.11.16.
 */
public class ChatList extends Message implements Serializable {
    private char[] chatsList;

    public ChatList() {
        super(null);
    }

    public char[] getChatsList() {
        return chatsList;
    }
}
