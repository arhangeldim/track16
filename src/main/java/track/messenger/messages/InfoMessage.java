package track.messenger.messages;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class InfoMessage extends Message {
    private Integer userId;

    public InfoMessage() {
        super(Type.MSG_INFO);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
