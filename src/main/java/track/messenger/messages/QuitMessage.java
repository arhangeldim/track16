package track.messenger.messages;

import track.messenger.User;

/**
 * Created by geoolekom on 14.11.16.
 */
public class QuitMessage extends Message {

    public QuitMessage() {
        super(null, Type.MSG_QUIT);
    }

    public QuitMessage(User user) {
        super(user, Type.MSG_QUIT);
    }
}
