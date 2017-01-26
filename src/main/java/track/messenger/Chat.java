package track.messenger;

import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.client.TextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 29.12.2016.
 */
public class Chat {
    private Long id;
    private List<TextMessage> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Chat(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<TextMessage> getMessages() {
        return messages;
    }

    public void addTextMessage(TextMessage message) {
        messages.add(message);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
