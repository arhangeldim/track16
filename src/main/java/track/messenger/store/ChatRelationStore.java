package track.messenger.store;

import track.messenger.messages.Chat;
import track.messenger.messages.ChatRelation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatRelationStore extends Store {

    public ChatRelationStore() {
        setClassName(ChatRelation.class.getName());
    }

    public Chat getChat(Integer id) {
        try {
            List<ChatRelation> relations = new LinkedList<>();
            get("chatId = '" + id + "'").forEach(object -> relations.add((ChatRelation) object));
            return new Chat(relations);
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить чат по id. " + e.toString());
            return null;
        }
    }

    public Chat getChat(Integer firstId, Integer secondId) {
        try {
            List<ChatRelation> relations = new LinkedList<>();
            get("adminId = '" + firstId + "' and participantId = '" + secondId +
            "' or adminId = '" + secondId + "' and participantId = '" + firstId + "'")
                    .forEach(object -> relations.add((ChatRelation) object));
            return new Chat(relations);
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось получить чат двух пользователей. " + e.toString());
            return null;
        }
    }

    public void saveChat(Chat chat) {
        try {
            save(chat.getRelations());
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить чат." + e.toString());
        }
    }

    public void saveRelations(List<ChatRelation> relations) {
        try {
            save(relations);
        } catch (Exception e) {
            System.out.println(this.getClass() + ": не удалось сохранить чат." + e.toString());
        }
    }
}
