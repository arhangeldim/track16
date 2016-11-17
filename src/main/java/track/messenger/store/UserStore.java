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
            insert.append("('" + user.getUsername() + "', '" + user.getPassword() + "'), ");
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
            users.add(user);
        }
        return users;
    }

    @Override
    public String columns() {
        return "(username, password)";
    }

    public User getUser(String username) {
        List<User> users = get("username = '" + username + "'");
        System.out.println(users.size());
        if (users != null) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User getUser(Integer id) {
        List<User> users = get("id = '" + id.toString() + "'");
        if (users != null) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public void saveUser(User user) {
        save(Collections.nCopies(1, user));
    }

}
