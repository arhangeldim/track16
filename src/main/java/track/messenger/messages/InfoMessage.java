package track.messenger.messages;

import java.util.Objects;

/**
 * Created by ksushar on 1/15/17.
 */
public class InfoMessage extends Message {
    private long userId;
    private String info;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
        return Objects.equals(userId, message.getUserId()) && Objects.equals(info, message.getInfo());
    }

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder(Long.toString(userId));
        builder.append("#");
        builder.append(info);
        return Objects.hash(super.hashCode(), builder.toString());
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "userId='" + Long.toString(userId) + "', " +
                "info='" + info + '\'' +
                '}';
    }
}
