package track.messenger.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.Chat;
import track.messenger.User;
import track.messenger.db.UserBase;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 02.12.16.
 */
public class UserFactory implements  UserStore{
    private Map<Long, User> users;
    static Logger log = LoggerFactory.getLogger(UserFactory.class);
    private UserBase userBase;
    private Map<String, Long> userLogins = new HashMap<>();

    public UserFactory(UserBase userBase) {
        this.userBase = userBase;
    }

    public boolean isUserExist(String login) {
        try {
            log.info("userName: {}", login);
            if (userBase.getUsertoLogin(login) == null)
                log.info("null!!");
            else
                return true;
            //return (userBase.getUsertoLogin(login) != null);
        } catch (Exception e) {
            System.err.println("this login doesn't exist:" + login);
        }
        return false;
    }


    @Override
    public User addUser(User user, long id) throws ClassNotFoundException, SQLException {
        try {
            userBase.addUser(user, id);
            return user;
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getUser(String login, String password) throws SQLException {
        return (userBase.getUser(login, password));
    }

    @Override
    public User getUserById(Long id) throws SQLException {
        return userBase.getUserById(id);
    }
    public List<Long> getUsersByChatId(Long chatId) throws SQLException {
        log.info("getchats");
        List<Long> users = userBase.getUsersByChatId(chatId);
        return users;
    }

    @Override
    public List<Long> getChatsByUserId(Long userId) throws SQLException {
        return userBase.getChatsByUserId(userId);
    }

    @Override
    public Chat getChatById(Long chatId) throws SQLException {
        Chat chat = new Chat();
        List<Long> userIds = getUsersByChatId(chatId);
        //List<Long> messagesIds = getMessagesFromChat(chatId);
        log.info("userIds: {}", userIds);
        for (Long userId : userIds) {
            chat.addParticipant(userId);
        }

        chat.setId(chatId);

        return chat;
    }

    @Override
    public List<Usermessage> getMessagesFromChat(Long chatId) throws SQLException {
        return userBase.getMessagesFromChat(chatId);
    }

    @Override
    public Message getMessageById(Long messageId) throws SQLException {
        return null;
    }

    @Override
    public void addMessage(Long chatId, TextMessage message) throws SQLException {
        userBase.addMessage(chatId, message);
    }

    @Override
    public void addUserToChat(Long userId, Long chatId) throws SQLException {

    }

    @Override
    public Chat createChat(Long id, String[] participants, long numchats) throws SQLException, ClassNotFoundException {
        return userBase.createChat(id, participants, numchats);
    }

    @Override
    public long Chat_size() throws SQLException {
        return userBase.Chat_size();
    }

}
