package track.messenger.messages.old;

import track.messenger.User;
import track.messenger.messages.Type;

/**
 * Created by geoolekom on 13.11.16.
 */

public class TextMessage extends Message {

    private String text;

    public String getText() {
        return text;
    }

    public TextMessage(User sender, String text) {
        super(sender, Type.MSG_TEXT);
        this.text = text;
    }
}
