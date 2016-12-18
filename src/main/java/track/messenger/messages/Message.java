package track.messenger.messages;

import java.io.Serializable;

/**
 *
 */
public abstract class Message implements Serializable {

    private Long id;
    private Long senderId;
    private Type type;

    public Message(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Type getType() {
        return type;
    }
}
