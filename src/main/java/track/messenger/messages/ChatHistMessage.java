package track.messenger.messages;

/**
 * Created by Tsepa Stas on 18.12.2016.
 */
public class ChatHistMessage extends Message {

    private Long chatId;

    public ChatHistMessage() {
        super(Type.MSG_CHAT_HIST);
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
