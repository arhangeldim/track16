package track.messenger.messages.client;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

public class LoginMessage extends Message {
    private String login;
    private String password;

    public LoginMessage() {
        super(Type.MSG_LOGIN);
    }

    public LoginMessage(String login, String password) {
        super(Type.MSG_LOGIN);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
