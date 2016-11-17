package track.messenger.messages;

import track.messenger.store.dao.User;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatHistMessage extends Message {
    private Integer chatId;

    public ChatHistMessage() {
        super(null, Type.MSG_CHAT_HIST);
    }

    public ChatHistMessage(User sender, String data) throws InstantiationException {
        super(sender, Type.MSG_CHAT_HIST);
        if (sender == null) {
            throw new InstantiationException("Вы не авторизованы. ");
        }
        try {
            this.chatId = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new InstantiationException("Введен не только id чата. ");
        }
    }

    public Integer getChatId() {
        return chatId;
    }
}
