package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;

/**
 * Created by geoolekom on 13.11.16.
 */

public class LoginMessage extends Message implements Serializable {
    private String username;
    private String password;

    protected LoginMessage() {
        super(null, Type.MSG_LOGIN);
    }

    public LoginMessage(User sender, String data) {
        super(sender, Type.MSG_LOGIN);

        String[] authData = data.split(" ");
        if (authData.length == 2) {
            username = authData[0];
            password = authData[1];
        } else {
            System.out.println("Введено что-то кроме логина и пароля.");
        }
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
