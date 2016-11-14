package track.messenger.store;

import track.messenger.User;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by geoolekom on 14.11.16.
 */

public class UserStore extends Store {

    public UserStore(String dbName) {
        super(User.class.getName(), dbName);
    }

    public User getUser(String username) {
        try {
            return User.class.cast(get("username = " + username));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по username.");
            return null;
        }
    }

    public User getUser(Long id) {
        try {
            return User.class.cast(get("id = " + id.toString()));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по id.");
            return null;
        }
    }

    public void saveUser(User user) {
        try {
            save(new LinkedList<User>(Collections.nCopies(1, user)));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить пользователя.");
        }
    }
}
