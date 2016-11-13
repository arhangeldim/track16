package track.messenger.store;

import track.messenger.User;

/**
 * Created by geoolekom on 14.11.16.
 */
public class UserStore {
    public User getUser(String username) {
        return new User(username, "1234");
    }

    public User getUser(Long id) {
        return new User("user", "1234");
    }

    public void add(User user) {

    }
}
