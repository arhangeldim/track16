package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */
public class Chat implements Serializable {
    private Integer id;
    private List<User> users = new ArrayList<>();
    private User admin;

    public Chat() {}

    public Chat(User user) {
        users.add(user);
        admin = user;
    }

    public Chat(User admin, List<User> users) {
        this.admin = admin;
        this.users = users;
    }

}
