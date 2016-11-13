package track.messenger.messages.old;

import track.messenger.User;
import track.messenger.messages.Type;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 */

public abstract class Message implements Serializable {

    private Long id;
    private Long senderId;
    private Type type;
    private LocalDateTime timestamp;

    public Message(User sender, Type type) {
        if (sender != null) {
            this.senderId = sender.getId();
        }
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public Type getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
