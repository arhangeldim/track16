package track.messenger.store;

import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */

public class MessageStore extends Store {

    public MessageStore(String dbName) {
        super(TextMessage.class.getName(), dbName);
    }

    public void saveMessage(TextMessage msg) {
        try {
            save(new LinkedList<TextMessage>(Collections.nCopies(1, msg)));
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить сообщение." + e.toString());
        }
    }

    public List<TextMessage> getChatHistory(Integer chatId) {
        try {
            List<Object> messageObjects = get("chatId = '" + chatId.toString() + "' order by timestamp");
            List<TextMessage> messages = new LinkedList<>();
            for (Object obj : messageObjects) {
                messages.add((TextMessage) obj);
            }
            return messages;
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить пользователя по id." + e.toString());
            return null;
        }
    }
}
