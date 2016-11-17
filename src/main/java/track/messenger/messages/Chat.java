package track.messenger.messages;

import track.messenger.store.dao.ChatRelation;
import track.messenger.store.dao.User;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by geoolekom on 14.11.16.
 */
public class Chat {
    private Integer id;
    private List<Integer> participants = new LinkedList<>();
    private Integer adminId;
    private List<ChatRelation> relations = new LinkedList<>();

    public Chat() {}

    public Chat(User user) {
        participants.add(user.getId());
        adminId = user.getId();
        relations.add(new ChatRelation(adminId, id, adminId));
    }

    public Chat(List<ChatRelation> relations) {
        if (relations == null || relations.size() == 0) {
            return;
        }
        this.id = relations.get(0).getChatId();
        this.adminId = relations.get(0).getAdminId();
        this.participants = relations.stream()
                .map(ChatRelation::getParticipantId)
                .collect(Collectors.toList());
        this.relations = relations;
    }

    public void addParticipant(User user) {
        if (user != null) {
            participants.add(user.getId());
            relations.add(new ChatRelation(adminId, id, user.getId()));
        }
    }

    public boolean contains(User user) {
        return participants.contains(user.getId());
    }

    public boolean contains(Integer userId) {
        return participants.contains(userId);
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public List<ChatRelation> getRelations() {
        return relations;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAdminId() {
        return adminId;
    }
}
