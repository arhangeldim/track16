package track.messenger;

import track.messenger.User;
import track.messenger.messages.Message;

import java.util.List;

/**
 * Created on 29.12.2016.
 */
public class Chat {
    private Long id;
    private List<Message> messages;
    private List<User> users;

    public Chat(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
