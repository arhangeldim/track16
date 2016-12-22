package track.messenger.store;

import track.messenger.Chat;
import track.messenger.messages.Message;
import track.messenger.messages.TextMessage;

import java.sql.SQLException;
import java.util.List;

public interface MessageStore {
    /**
     * получаем список ид пользователей заданного чата
     */
    List<Long> getChatsByUserId(Long userId) throws SQLException;

    /**
     * получить информацию о чате
     */
    Chat getChatById(Long chatId) throws SQLException;

    /**
     * Список сообщений из чата
     */
    List<Long> getMessagesFromChat(Long chatId) throws SQLException;

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

    Chat createChat(Long id, String[] participants) throws SQLException, ClassNotFoundException;
}
