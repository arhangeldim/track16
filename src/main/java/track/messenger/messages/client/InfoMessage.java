package track.messenger.messages.client;


import track.messenger.messages.Message;
import track.messenger.messages.Type;

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
