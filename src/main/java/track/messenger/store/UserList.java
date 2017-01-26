package track.messenger.store;

import track.messenger.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ksushar on 1/26/17.
 */
public class UserList implements UserStore {
    List<User> userList;

    UserList() {
        userList = new ArrayList<User>();
    }

    @Override
    public User addUser(User user) {
        userList.add(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return user;
    }

    @Override
    public User getUser(String login, String pass) {
        for (User user : userList) {
            if (user.getLogin().equals(login) && user.getPassword().equals(pass)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
