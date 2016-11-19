package track.messenger.store;

import track.messenger.store.dao.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */

public class UserStore extends AbstractStore<User> {

    @Override
    public String values(List<User> objects) {
        StringBuilder insert = new StringBuilder();
        for (User user : objects) {
            insert.append("('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getSalt() + "'), ");
        }
        String value = insert.toString();
        return value.substring(0, value.length() - 2);
    }

    @Override
    public List<User> fill(ResultSet resultSet) throws SQLException {
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setSalt(resultSet.getString("salt"));
            users.add(user);
        }
        if (users.isEmpty()) {
            return null;
        } else {
            return users;
        }
    }

    @Override
    public String columns() {
        return "(username, password, salt)";
    }

    public User getUser(String username) {
        List<User> users = get("username = '" + username + "'");
        if (users != null) {
            User user = users.get(0);
            user.setSalt(null);
            return user;
        } else {
            return null;
        }
    }

    public User getUser(Integer id) {
        List<User> users = get("id = '" + id.toString() + "'");
        if (users != null) {
            User user = users.get(0);
            user.setSalt(null);
            return user;
        } else {
            return null;
        }
    }

    public String getUserSalt(String username) {
        List<User> users = get("username = '" + username + "'");
        if (users != null) {
            return users.get(0).getSalt();
        } else {
            return null;
        }
    }

    @Override
    public Class getDataClass() {
        return User.class;
    }

    public void saveUser(User user) {
        save(Collections.nCopies(1, user));
    }

}
