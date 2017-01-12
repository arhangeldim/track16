package track.messenger.store;

import track.messenger.Chat;
import track.messenger.messages.Message;
import track.messenger.messages.client.TextMessage;

import java.util.List;

public interface MessageStore extends BaseStore {
    List<Long> getChatIdsByUserId(Long userId);

    Chat getChatById(Long chatId);

    Chat addChat(Chat chat);

    List<Message> getMessagesFromChat(Long chatId);

    Message getMessageById(Long messageId);

    void addMessage(Message message);

    void addUserToChat(Long userId, Long chatId);

}
