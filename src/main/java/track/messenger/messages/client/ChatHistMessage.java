package track.messenger.messages.client;

import track.messenger.messages.Message;
import track.messenger.messages.Type;

public class ChatHistMessage extends Message {

    private Long chatId;

    public ChatHistMessage() {
        super(Type.MSG_CHAT_HIST);
    }

    public ChatHistMessage(Long chatId) {
        super(Type.MSG_CHAT_HIST);
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
