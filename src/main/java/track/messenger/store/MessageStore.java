package track.messenger.store;

import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 14.11.16.
 */

public class MessageStore extends Store {

    public MessageStore() {
        setClassName(TextMessage.class.getName());
    }

    public void saveMessage(TextMessage msg) {
        try {
            save(Collections.nCopies(1, msg));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить сообщение." + e.toString());
        }
    }

    public List<TextMessage> getChatHistory(Integer chatId) {
        try {
            List<Object> messageObjects = get("chatId = '" + chatId.toString() + "' order by timestamp");
            return messageObjects.stream().map(obj -> (TextMessage) obj).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по id." + e.toString());
            return null;
        }
    }
}
