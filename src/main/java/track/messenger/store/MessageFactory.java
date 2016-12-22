package track.messenger.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.db.MessageBase;
import track.messenger.db.UserBase;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by asus on 02.12.16.
 */
public class MessageFactory implements MessageStore{
    UserBase userBase;
    MessageBase messageBase;
    static Logger log = LoggerFactory.getLogger(MessageFactory.class);

    public MessageFactory(UserBase userBase, MessageBase messageBase) {
        this.messageBase = messageBase;
        this.userBase = userBase;
    }


    public List<Long> getUsersByChatId(Long chatId) throws SQLException {
        List<Long> users = userBase.getUsersByChatId(chatId);
        return users;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) throws SQLException {
        return null;
    }

    @Override
    public Chat getChatById(Long chatId) throws SQLException {
        Chat chat = new Chat();
        List<Long> userIds = getUsersByChatId(chatId);
        List<Long> messagesIds = getMessagesFromChat(chatId);
        log.info("userIds: {}", userIds);
        for (Long userId : userIds) {
            chat.addParticipant(userId);
        }

        chat.setId(chatId);

        return chat;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) throws SQLException {
        return null;
    }

    @Override
    public Message getMessageById(Long messageId) throws SQLException {
        return null;
    }

    @Override
    public void addMessage(Long chatId, TextMessage message) throws SQLException {

    }

    @Override
    public void addUserToChat(Long userId, Long chatId) throws SQLException {

    }

    @Override
    public Chat createChat(Long id, String[] participants) throws SQLException, ClassNotFoundException {
        return messageBase.createChat(id, participants);
    }
}
