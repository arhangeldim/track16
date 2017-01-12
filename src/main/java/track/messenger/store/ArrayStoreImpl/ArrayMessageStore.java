package track.messenger.store.ArrayStoreImpl;

import track.messenger.Chat;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.client.TextMessage;
import track.messenger.store.MessageStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created on 12.01.2017.
 */
public class ArrayMessageStore implements MessageStore {

    private List<Chat> chatList = new ArrayList<>();
    private Long freeChatId = 0L;

    @Override
    public List<Long> getChatIdsByUserId(Long userId) {
        return chatList
                .stream()
                .filter((chat) -> chat.getUsers()
                        .stream()
                        .anyMatch((user -> Objects.equals(user.getId(), userId))))
                .map(Chat::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Chat getChatById(Long chatId) {
        for (Chat chat : chatList) {
            if (chat.getId().equals(chatId)) {
                return chat;
            }
        }
        return null;
    }

    @Override
    public Chat addChat(Chat chat) {
        chat.setId(getFreeChatId());
        chatList.add(chat);
        return chat;
    }

    @Override
    public List<TextMessage> getMessagesFromChat(Long chatId) {
        return getChatById(chatId).getMessages();
    }

    @Override
    public Message getMessageById(Long messageId) {
        for (Chat chat : chatList) {
            for (Message message : chat.getMessages())
                if (message.getId().equals(messageId))
                    return message;
        }
        return null;
    }

    @Override
    public void addTextMessage(TextMessage message) {
        getChatById(message.getChatId()).addTextMessage(message);
    }

    @Override
    public void addUserToChat(User user, Long chatId) {
        getChatById(chatId).addUser(user);
    }

    private Long getFreeChatId() {
        freeChatId++;
        return freeChatId;
    }
}
