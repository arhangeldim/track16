package track.messenger.store;

import track.messenger.messages.client.TextMessage;

import java.util.List;

public interface MessageStore extends BaseStore {
    List<Long> getChatsByUserId(Long userId);

    //Chat getChatById(Long chatId);

    List<Long> getMessagesFromChat(Long chatId);

    TextMessage getMessageById(Long messageId);

    void addMessage(Long chatId, TextMessage message);

    void addUserToChat(Long userId, Long chatId);

}
