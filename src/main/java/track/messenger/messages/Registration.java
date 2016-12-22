package track.messenger.messages;

import java.io.Serializable;

/**
 * Created by ivan on 21.11.16.
 */
public class Registration extends Message implements Serializable {
    private String username;
    private String password;
    public Registration() {
        super(null);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
