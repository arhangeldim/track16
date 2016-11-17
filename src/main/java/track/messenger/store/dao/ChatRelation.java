package track.messenger.store.dao;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }
}
