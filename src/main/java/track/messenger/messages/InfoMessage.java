package track.messenger.messages;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class InfoMessage extends Message {
    private Long userId;

    public InfoMessage() {
        super(Type.MSG_INFO);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
