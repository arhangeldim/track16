package track.messenger.messages;

/**
 * Created by user on 27.11.16.
 */
public class SignUpMessage extends LoginMessage{
    public SignUpMessage(Long id, Long SenderId, Type type, String login, String password) {
        super(id, SenderId, type, login, password);
    }
}
