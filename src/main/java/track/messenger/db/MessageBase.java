package track.messenger.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.store.MessageStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by asus on 02.12.16.
 */
public class MessageBase implements MessageStore{
    private DBConnectionManager dBConnectionManager;
    private Connection connection;
    static Logger log = LoggerFactory.getLogger(MessageBase.class);

    public MessageBase() throws SQLException, ClassNotFoundException {
        dBConnectionManager = new DBConnectionManager();
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) throws SQLException {
        dBConnectionManager.connect();
        dBConnectionManager.setResultset("SELECT user_id " +
                "FROM Chat_User " +
                "WHERE chat_id =" + userId);
        return dBConnectionManager.searchchats(userId);
    }

    @Override
    public Chat getChatById(Long chatId) throws SQLException {
        return null;
    }

    @Override
    public List<Long> getMessagesFromChat(Long chatId) throws SQLException {
        dBConnectionManager.connect();
        dBConnectionManager.setResultset("SELECT id " +
                "FROM Messages " +
                "WHERE chat_id =" + chatId);
        return dBConnectionManager.searchMessages(chatId);
    }

    @Override
    public Message getMessageById(Long messageId) throws SQLException {
        dBConnectionManager.connect();
        dBConnectionManager.setResultset("SELECT * " +
                "FROM Messages " +
                "WHERE id =" + messageId);
        return dBConnectionManager.getMessage(messageId);
    }

    @Override
    public void addMessage(Long chatId, TextMessage message) throws SQLException {
        dBConnectionManager.connect();
        dBConnectionManager.setsql("INSERT INTO Messages " +
                "(text, chat_id, timestamp, user_id) VALUES " +
                "(" + message.getText() + ","+ chatId + "," + message.getSenderId() + "," + message.getTime() + ") ");
        dBConnectionManager.commit();
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) throws SQLException {
        dBConnectionManager.connect();
        dBConnectionManager.setsql("INSERT INTO userchats (\"chat_id\", \"user_id\") " +
                "VALUES " +
                "(" + chatId + ","+ userId +"); ");
        dBConnectionManager.commit();
    }

    @Override
    public Chat createChat(Long id, String[] participants) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement(
                "INSERT INTO Chat_User ('chat_id', 'user_id') VALUES (?, ?)");
        log.info("id : {}", id);
        //log.info("partisipand: {}", participanе);
        pstmt.setString(1, "0");
        pstmt.setString(2, "2");
        pstmt.executeUpdate();
        /*for (String participant: participants) {
            if (Objects.equals(participant, "/chat_create"))
                continue;
            pstmt = connection.prepareStatement(
                    "INSERT INTO Chat_User ('chat_id', 'user_id') VALUES (?, ?)");
            log.info("id : {}", id);
            log.info("partisipand: {}", participant);
            pstmt.setString(1, "0");
            pstmt.setString(2, "1");
            pstmt.executeUpdate();
            break;
        }*/
        return null;
    }
}
