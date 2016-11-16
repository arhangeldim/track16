package track.messenger.messages;

import java.io.Serializable;

/**
 * Created by geoolekom on 16.11.16.
 */
public class ChatRelation implements Serializable {
    private Integer id;
    private Integer adminId;
    private Integer chatId;
    private Integer participantId;

    public ChatRelation() {}

    public ChatRelation(Integer adminId, Integer chatId, Integer participantId) {
        this.adminId = adminId;
        this.chatId = chatId;
        this.participantId = participantId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getChatId() {
        return chatId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public Integer getParticipantId() {
        return participantId;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAdminId(String adminId) {
        this.adminId = Integer.parseInt(adminId);
    }

    public void setChatId(String chatId) {
        this.chatId = Integer.parseInt(chatId);
    }

    public void setParticipantId(String participantId) {
        this.participantId = Integer.parseInt(participantId);
    }
}
