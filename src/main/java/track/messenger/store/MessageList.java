package track.messenger.store;

import track.messenger.User;
import track.messenger.messages.Message;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ksushar on 1/26/17.
 */
public class MessageList implements MessageStore {

    private class Chat {
        List<Long> userIds;
        Map<Long, Message> messages;

        Chat() {
            userIds = new ArrayList<Long>();
            messages = new HashMap<Long, Message>();
        }
    }

    Map<Long, Chat> chats;

    MessageList() {
        chats = new HashMap<>();
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) {
        List<Long> chatsIds = chats.keySet().stream().filter(
                id -> chats.get(id).userIds.contains(userId)
        ).collect(Collectors.toList());
        return chatsIds;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) {
        List<Long> messageIds = new ArrayList<>(chats.get(chatId).messages.keySet());
        return messageIds;
    }

    @Override
    public Message getMessageById(Long messageId) {
        for (Chat chat : chats.values()) {
            if (chat.messages.containsKey(messageId)) {
                return chat.messages.get(messageId);
            }
        }
        return null;
    }

    @Override
    public void addMessage(Long chatId, Message message) {
//        if (chats.containsKey(chatId)) {
//            if (chats.get(chatId).userIds.contains(message.getSenderId())) {
//                chats.get(chatId).messages.put(message.getId(), message);
//            }
//        };
        chats.get(chatId).messages.put(message.getId(), message);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) {
        chats.get(chatId).userIds.add(userId);
    }
}
