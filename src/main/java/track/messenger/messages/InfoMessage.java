package track.messenger.messages;

import java.util.Objects;

/**
 * Created by ksushar on 1/15/17.
 */
public class InfoMessage extends Message {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = new Long(userId);
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
        InfoMessage message = (InfoMessage) other;
        return Objects.equals(userId, message.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Long.toString(userId).toString());
    }

    @Override
    public String toString() {
        if (userId == null) {
            return "{InfoMessage{userId=-1})";
        }
        return "InfoMessage{" +
                "userId='" + Long.toString(userId) +
                "'}";
    }
}
