package track.messenger.store;

import track.messenger.messages.Chat;
import track.messenger.store.dao.ChatRelation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatRelationStore extends AbstractStore<ChatRelation> {

    @Override
    public void setup(PreparedStatement statement, ChatRelation relation) throws SQLException {
        statement.setInt(1, relation.getAdminId());
        statement.setInt(2, relation.getChatId());
        statement.setInt(3, relation.getParticipantId());
    }

    @Override
    public String values() throws SQLException {
        return "(?, ?, ?)";
    }

    @Override
    public List<ChatRelation> fill(ResultSet resultSet) throws SQLException {
        List<ChatRelation> messages = new LinkedList<>();
        while (resultSet.next()) {
            ChatRelation relation = new ChatRelation();
            relation.setId(resultSet.getInt("id"));
            relation.setAdminId(resultSet.getInt("adminId"));
            relation.setChatId(resultSet.getInt("chatId"));
            relation.setParticipantId(resultSet.getInt("participantId"));
            messages.add(relation);
        }
        return messages;
    }

    @Override
    public String columns() {
        return "(adminId, chatId, participantId)";
    }

    public Chat getChat(Integer id) {
        List<ChatRelation> relations = get("chatId = '" + id + "'");
        if (relations == null || relations.size() == 0) {
            return null;
        } else {
            return new Chat(relations);
        }
    }

    public Chat getChat(Integer firstId, Integer secondId) {
        List<ChatRelation> relations = get("adminId = '" + firstId + "' and participantId = '" + secondId +
                "' or adminId = '" + secondId + "' and participantId = '" + firstId + "'");
        if (relations == null || relations.size() == 0) {
            return null;
        } else {
            return new Chat(relations);
        }
    }

    public List<Integer> getUserChats(Integer userId) {
        return get("participantId = '" + userId + "'").stream()
                .map(ChatRelation::getChatId)
                .collect(Collectors.toList());
    }

    @Override
    public Class getDataClass() {
        return ChatRelation.class;
    }

    public void saveChat(Chat chat) {
        save(chat.getRelations());
    }

    public void saveRelations(List<ChatRelation> relations) {
        save(relations);
    }

    public Integer getNewChatId() {
        return getMax("chatId") + 1;
    }
}
