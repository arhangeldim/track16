package track.messenger;

import track.messenger.store.UserStore;

import java.io.Serializable;

/**
 *
 */

public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Username:\t" + username.toString() + "\nPassword:\t" + password.toString();
    }
}
