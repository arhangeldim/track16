package track.messenger.messages;

import track.messenger.User;

/**
 * Created by geoolekom on 13.11.16.
 */

public class InfoMessage extends Message {

    private Long requestedUser;

    public Long getRequestedUser() {
        return requestedUser;
    }

    public InfoMessage(User sender, String data) {
        super(sender, Type.MSG_INFO);
        if (sender == null) {
            System.out.println("Вы не авторизованы");
        } else {
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
}
