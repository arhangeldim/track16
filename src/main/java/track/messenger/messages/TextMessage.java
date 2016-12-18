package track.messenger.messages;

import java.util.Objects;

/**
 * Простое текстовое сообщение
 */
public class TextMessage extends Message {
    private Long chatId;
    private String text;

    public TextMessage() {
        super(Type.MSG_TEXT);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TextMessage message = (TextMessage) other;
        return Objects.equals(text, message.text) && Objects.equals(chatId, message.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "chatId=" + chatId + ", " +
                "text='" + text + '\'' +
                '}';
    }
}