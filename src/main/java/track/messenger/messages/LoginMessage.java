package track.messenger.messages;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class LoginMessage extends Message {
    private String username;
    private String password;

    public LoginMessage() {
        super(Type.MSG_LOGIN);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
