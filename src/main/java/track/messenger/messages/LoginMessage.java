package track.messenger.messages;

/**
 * Created by user on 21.11.16.
 */
public class LoginMessage extends Message {

    private String login, password;

    public LoginMessage(Long id, Long SenderId, Type type, String login, String password) {
        super();
        setId(id);
        setSenderId(SenderId);
        setType(type);
        setLogin(login);
        setPassword(password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
