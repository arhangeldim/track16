package track.messenger.store;

import track.messenger.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */

public class UserStore extends Store {

    private String hashAlgorithm;

    public UserStore() {
        setClassName(User.class.getName());
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public User getUser(String username) {
        try {
            List<Object> users = get("username = '" + username + "'");
            return (User) users.get(0);
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по username. " + e.toString());
            return null;
        }
    }

    public User getUser(Integer id) {
        try {
            List<Object> users = get("id = '" + id.toString() + "'");
            return (User) users.get(0);
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по id." + e.toString());
            return null;
        }
    }

    public void saveUser(User user) {
        try {
            MessageDigest hasher = MessageDigest.getInstance(hashAlgorithm);
            hasher.update(user.getPassword().getBytes());
            String encryptedPassword = new String(hasher.digest());
            user.setPassword(encryptedPassword);
            save(Collections.nCopies(1, user));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить пользователя. " + e.toString());
        }
    }
}
