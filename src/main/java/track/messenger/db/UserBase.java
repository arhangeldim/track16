package track.messenger.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;
import track.messenger.store.UserStore;
import track.messenger.store.Usermessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by asus on 30.11.16.
 */
public class UserBase implements UserStore {

    static Logger log = LoggerFactory.getLogger(UserBase.class);
    private DBConnectionManager dBConnectionManager;
    private Connection connection;

    public UserBase() throws SQLException, ClassNotFoundException {
        dBConnectionManager = new DBConnectionManager();
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");

    }

    @Override
    public User addUser(User user, long id) throws ClassNotFoundException, SQLException {
        PreparedStatement pstmt = null;
        log.info("id: {}", id);
        pstmt = connection.prepareStatement(
                "INSERT INTO User ('id', 'login', 'password') VALUES (?, ?, ?)");
        pstmt.setString(1, String.valueOf(id));
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());
        pstmt.executeUpdate();
        return user;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getUser(String login, String password) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement(
                "SELECT id, login, password FROM User where login = ? and password = ?;");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            log.info("id: {}", rs.getLong("id"));
            log.info("login: {}", rs.getString("login"));
            log.info("password: {}", rs.getString("Password"));
            User user = new User(login);
            user.setPassword(rs.getString("Password"));
            user.setId(rs.getLong("id"));
            return user;
        }
        return  null;
    }

    public User getUsertoLogin(String login) throws SQLException, ClassNotFoundException {
        log.info("Begin");
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement(
                "SELECT id, login, password FROM User where login = ?");
        pstmt.setString(1, login);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next())
        {
            log.info("id: {}", rs.getLong("id"));
            log.info("login: {}", rs.getString("login"));
            log.info("password: {}", rs.getString("Password"));
            User user = new User(login);
            user.setPassword(rs.getString("Password"));
            user.setId(rs.getLong("id"));
            return user;
        }
        return null;
    }

    @Override
    public User getUserById(Long id) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("SELECT * from User WHERE id = ?;");
        pstmt.setString(1, String.valueOf(id));
        ResultSet rs = pstmt.executeQuery();
        User user = new User();
        user.setId(id);
        while (rs.next()) {
            user.setName(rs.getString("login"));
            user.setPassword(rs.getString("password"));
        }
        return user;
    }

    public List<Long> getUsersByChatId(Long chatId) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("SELECT user_id FROM Chat_User WHERE chat_id = ?;");
        pstmt.setString(1, String.valueOf(chatId));
        List<Long> msgList = new ArrayList<Long>();
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            msgList.add(rs.getLong("user_id"));
        }
        return msgList;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) throws SQLException {
        PreparedStatement pstmt = null;
        log.info("user_id: {}", userId);
        pstmt = connection.prepareStatement("SELECT chat_id FROM Chat_User WHERE user_id = ?;");
        pstmt.setString(1, String.valueOf(userId));
        List<Long> msgList = new ArrayList<Long>();
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            msgList.add(rs.getLong("chat_id"));
            log.info("msgList: {}", msgList);
        }
        return msgList;
    }

    @Override
    public Chat getChatById(Long chatId) throws SQLException {
        return null;
    }

    @Override
    public List<Usermessage> getMessagesFromChat(Long chatId) throws SQLException {
        log.info("chatId: {}", chatId);
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("SELECT * from Message WHERE chat_id = ?;");
        pstmt.setString(1, String.valueOf(chatId));
        List<String> msgList = new ArrayList<>();
        List<String> userList = new ArrayList<>();
        List<Usermessage> usermessages = new ArrayList<>();
        ResultSet rs = pstmt.executeQuery();
        Usermessage usermessage = null;
        while (rs.next()) {
            usermessage = new Usermessage();
            usermessage.setMessage(rs.getString("text"));
            usermessage.setUser(getUserById(rs.getLong("user_id")));
            usermessages.add(usermessage);
            msgList.add(rs.getString("text"));
            userList.add(getUserById(rs.getLong("user_id")).getName());
        }
        log.info("Array: {}", msgList);
        log.info("Names: {}", userList);
        log.info("Names: {}", usermessages);
        return usermessages;
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
        log.info("onadd");
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("INSERT INTO Message ('text', 'chat_id', 'timestamp', 'user_id') " +
                "VALUES (?, ?, ? , ?);");
        pstmt.setString(1, String.valueOf(message.getText()));
        pstmt.setString(2, String.valueOf(chatId));
        pstmt.setString(3, String.valueOf(message.getTime()));
        pstmt.setString(4, String.valueOf(message.getSenderId()));
        pstmt.executeUpdate();
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("INSERT INTO Chat ('chat_id', 'user_id') VALUES (?, ?);");
        pstmt.setString(1, String.valueOf(userId));
        pstmt.setString(2, String.valueOf(chatId));
        pstmt.executeUpdate();
    }

    @Override
    public Chat createChat(Long id, String[] participants, long numchats) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement("INSERT INTO Chat ('id', 'owner_id') VALUES (?, ?);");
        pstmt.setString(1, String.valueOf(numchats));
        pstmt.setString(2, String.valueOf(id));
        pstmt.executeUpdate();
        for (String participant: participants) {
            if (Objects.equals(participant, "/chat_create"))
                continue;
            pstmt = connection.prepareStatement(
                    "INSERT INTO Chat_User ('chat_id', 'user_id') VALUES (?, ?);");
            log.info("id : {}", numchats);
            log.info("partisipand: {}", participant);
            pstmt.setString(1, String.valueOf(numchats));
            pstmt.setString(2, participant);
            pstmt.executeUpdate();
        }
        Chat chat = new Chat();
        chat.setId(numchats);
        return chat;
    }

    @Override
    public long Chat_size() throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement(
                "SELECT * FROM (Chat) ;", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pstmt.executeQuery();
        long count = 0;
        while(rs.next())
            count++;
        return count;
    }

    public long User_size() throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = connection.prepareStatement(
                "SELECT * FROM (User) ;", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pstmt.executeQuery();
        long count = 0;
        while(rs.next())
            count++;
        return count;
    }
}
