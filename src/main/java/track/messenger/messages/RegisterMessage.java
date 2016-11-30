package track.messenger.messages;

/**
 * Created by geoolekom on 16.11.16.
 */
public class RegisterMessage extends Message {

    private String username;
    private String password;

    protected RegisterMessage() {
        super(null, Type.MSG_REGISTER);
    }

    public RegisterMessage(String data) throws InstantiationException {
        super(null, Type.MSG_REGISTER);
        String[] authData = data.split(" ");
        if (authData.length == 2) {
            username = authData[0].trim();
            password = authData[1].trim();
        } else {
            throw new InstantiationException("Введено что-то кроме логина и пароля.");
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
