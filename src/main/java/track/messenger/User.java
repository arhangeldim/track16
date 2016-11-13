package track.messenger;

/**
 *
 */

public class User {
    private long id;
    private String username;
    private String password;

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
        return "Username:\t" + username.toString() + "\nPassword:\t" + password.toString() + "\n";
    }
}
