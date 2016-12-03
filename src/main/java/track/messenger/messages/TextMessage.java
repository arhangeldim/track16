package track.messenger.messages;

import java.util.Objects;

/**
 * Created by user on 23.11.16.
 */
public class TextMessage extends Message {

    private String text;
    private Long SendToId;

    public TextMessage(Long sendToId) {
        SendToId = sendToId;
        super.setType(Type.MSG_STATUS);
    }

    public TextMessage() {
    }

    public TextMessage(Long i, Long i1, String s) {
        super.setSenderId(i);
        setSendToId(i1);
        setText(s);
    }

    public Long getSendToId() {
        return SendToId;
    }

    public void setSendToId(Long sendToId) {
        SendToId = sendToId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        TextMessage message = (TextMessage) other;
        return Objects.equals(text, message.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
