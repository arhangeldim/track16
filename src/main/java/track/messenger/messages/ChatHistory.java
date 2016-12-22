package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;

/**
 * Created by ivan on 21.11.16.
 */
public class ChatHistory extends Message implements Serializable {
    public ChatHistory() {
        super(null);
    }
}
