package track.messenger.messages;

/**
 * Типы сообщений в системе
 */
public enum Type {
    // Сообщения от клиента к серверу
    MSG_LOGIN, // в ответ MSG_STATUS
    MSG_TEXT, // в ответ MSG_STATUS
    MSG_INFO, // в ответ MSG_INFO_RESULT
    MSG_CHAT_LIST, // в ответ MSG_CHAT_LIST_RESULT,
    MSG_CHAT_CREATE, // в ответ MSG_STATUS
    MSG_CHAT_HIST, // в ответ MSG_CHAT_HIST_RESULT,

    // Сообщения от сервера клиенту
    MSG_STATUS,
    MSG_CHAT_LIST_RESULT,
    MSG_CHAT_HIST_RESULT,
    MSG_INFO_RESULT
}
