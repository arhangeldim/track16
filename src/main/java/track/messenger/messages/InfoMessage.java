package track.messenger.messages;

import track.messenger.store.dao.User;

import java.io.Serializable;

/**
 * Created by geoolekom on 13.11.16.
 */

public class InfoMessage extends Message implements Serializable {

    private Integer requestedUser;

    public Integer getRequestedUser() {
        return requestedUser;
    }

    protected InfoMessage() {
        super(null, Type.MSG_INFO);
    }

    public InfoMessage(User sender, String data) throws InstantiationException {
        super(sender, Type.MSG_INFO);
        if (sender == null) {
            throw new InstantiationException("Вы не авторизованы.");
        }
        if ("".equals(data)) {
            requestedUser = sender.getId();
        } else {
            try {
                requestedUser = Integer.parseInt(data);
            } catch (NumberFormatException nfe) {
                throw new InstantiationException("Введено не число.");
            }
        }
    }
}
