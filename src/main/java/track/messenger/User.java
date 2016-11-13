package track.messenger;

import track.messenger.store.UserStore;

import java.io.Serializable;

/**
 *
 */

public class User implements Serializable {
    private long id;
    private String username;
    private String password;
    public static UserStore database = new UserStore();

    public User(String username, String password) {
        this.id = 1;
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
        return "Username:\t" + username.toString() + "\nPassword:\t" + password.toString() + "\n";
    }
}
