package track.messenger.messages.client;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class LoginMessage extends Message {
    private String username;
    private String password;

    public LoginMessage(String username, String password) {
        super(Type.MSG_LOGIN);
        this.username = username;
        this.password = password;
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
