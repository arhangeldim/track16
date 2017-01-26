package track.messenger.messages;

import java.util.Objects;

/**
 * Created by ksushar on 1/15/17.
 */
public class LoginMessage extends Message {

    private String login;
    private String password;

    public LoginMessage() {
        this.setType(Type.MSG_LOGIN);
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        LoginMessage message = (LoginMessage) other;
        return login.equals(message.getLogin()) && password.equals(message.getPassword());
    }

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder(login);
        builder.append("#");
        builder.append(password);
        return Objects.hash(super.hashCode(), builder.toString());
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "login='" + login + "', " +
                "password='" + password + '\'' +
                '}';
    }
}
