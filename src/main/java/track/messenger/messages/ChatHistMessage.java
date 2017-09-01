package track.messenger.messages;

import java.util.List;
import java.util.Objects;

/**
 * Сообщение со всей историей сообщений
 */
public class ChatHistMessage extends Message {
    private long chatId;

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
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
        if (!super.equals(other)) {
            return false;
        }
        ChatHistMessage message = (ChatHistMessage) other;
        return chatId == message.getChatId();
    }

    @Override
    public String toString() {
        return "ChatHistMessage{ chatId " + Long.toString(chatId) + "}";
    }
}