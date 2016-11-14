package track.messenger.store;

import track.messenger.User;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */

public class UserStore extends Store {

    public UserStore(String dbName) {
        super(User.class.getName(), dbName);
        //saveUser(new User("geoolekom", "qwerty"));
    }

    public User getUser(String username) {
        try {
            List<Object> users = get("username = '" + username + "'");
            return User.class.cast(users.get(0));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по username. " + e.toString());
            return null;
        }
    }

    public User getUser(Long id) {
        try {
            List<Object> users = get("id = '" + id.toString() + "'");
            return User.class.cast(users.get(0));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по id." + e.toString());
            return null;
        }
    }

    public User loginUser(String username, String password) {
        try {
            List<Object> users = get("username = '" + username + "' and password = '" + password + "'");
            return User.class.cast(users.get(0));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось авторизоваться. " + e.toString());
            return null;
        }
    }

    public void saveUser(User user) {
        try {
            save(new LinkedList<User>(Collections.nCopies(1, user)));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить пользователя." + e.toString());
        }
    }
}
