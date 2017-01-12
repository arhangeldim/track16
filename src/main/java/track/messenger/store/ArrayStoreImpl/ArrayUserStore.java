package track.messenger.store.ArrayStoreImpl;

import track.messenger.User;
import track.messenger.store.UserStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12.01.2017.
 */
public class ArrayUserStore implements UserStore {
    private Long freeUserId = 0L;
    private List<User> userList = new ArrayList<>();

    @Override
    public User addUser(User user) {
        user.setId(getFreeUserId());
        userList.add(user);
        return user;
    }

    @Override
    public User getUser(String login) {
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Long getFreeUserId() {
        freeUserId++;
        return freeUserId;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }
}
