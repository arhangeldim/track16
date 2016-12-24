package track.messenger.messages;

import java.lang.reflect.*;

/**
 * Created by Александр on 25.12.2016.
 */
public class StatusMessage extends Message {
    public StatusMessage() {
        super(Type.MSG_STATUS);
    }
}
