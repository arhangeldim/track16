package track.messenger.store;

import track.messenger.Chat;
import track.messenger.User;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.sql.SQLException;
import java.util.List;

public interface UserStore {
    /**
     * Добавить пользователя в хранилище
     * Вернуть его же
     */
    User addUser(User user, long id) throws ClassNotFoundException, SQLException;

    /**
     * Обновить информацию о пользователе
     */
    User updateUser(User user);

    /**
     *
     * Получить пользователя по логину/паролю
     * return null if user not found
     */
    User getUser(String login, String password) throws SQLException;

    /**
     *
     * Получить пользователя по id, например запрос информации/профиля
     * return null if user not found
     */
    User getUserById(Long id) throws SQLException;

    List<Long> getChatsByUserId(Long userId) throws SQLException;

    /**
     * получить информацию о чате
     */
    Chat getChatById(Long chatId) throws SQLException;

    /**
     * Список сообщений из чата
     */
    List<Usermessage> getMessagesFromChat(Long chatId) throws SQLException;

    /**
     * Получить информацию о сообщении
     */
    Message getMessageById(Long messageId) throws SQLException;

    /**
     * Добавить сообщение в чат
     */
    void addMessage(Long chatId, TextMessage message) throws SQLException;

    /**
     * Добавить пользователя к чату
     */
    void addUserToChat(Long userId, Long chatId) throws SQLException;

    Chat createChat(Long id, String[] participants, long numchats) throws SQLException, ClassNotFoundException;

    long Chat_size() throws SQLException;
}
