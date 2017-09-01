package track.messenger.messages;

import java.util.List;
import java.util.Objects;


/**
 * Created by ksushar on 1/26/17.
 */
public class ChatHistResultMessage extends Message {
    private long chatId;
    private List<Message> chatHist;

    public long getChatId() {
        return chatId;
    }

    public List<Message> getChatHist() {
        return chatHist;
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
        ChatHistResultMessage message = (ChatHistResultMessage) other;
        if (chatId != message.getChatId()) {
            return false;
        }
        return chatHist.equals(message.getChatHist());
    }

    @Override
    public String toString() {
        return "ChatHistResultMessage{ chatId= " + Long.toString(chatId) + ", " +
                "history = " + chatHist.toString() + "}";
    }
}
