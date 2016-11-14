package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;

/**
 * Created by geoolekom on 13.11.16.
 */

public class InfoMessage extends Message implements Serializable {

    private Long requestedUser;

    public Long getRequestedUser() {
        return requestedUser;
    }

    protected InfoMessage() {
        super(null, Type.MSG_INFO);
    }

    public InfoMessage(User sender, String data) {
        super(sender, Type.MSG_INFO);
        if ("".equals(data)) {
            requestedUser = sender.getId();
        } else {
            try {
                requestedUser = Long.parseLong(data);
            } catch (NumberFormatException nfe) {
                System.out.println("Введено не число");
            }
        }
    }
}
