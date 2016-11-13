package track.messenger.messages;

import track.messenger.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * Простое текстовое сообщение
 */
public class TextMessage extends Message implements Serializable {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextMessage() {
        super(null, Type.MSG_TEXT);
        this.text = "";
    }

    public TextMessage(User sender, String text) {
        super(sender, Type.MSG_TEXT);
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
        return Objects.equals(text, message.text);
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