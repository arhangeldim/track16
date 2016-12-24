package track.messenger.messages;

import java.util.EnumMap;

/**
 * Created by Александр on 25.12.2016.
 */
public class MessageClassFactory {
    private static EnumMap<Type, Class<? extends Message>> messageClassMap;
    {
        messageClassMap = new EnumMap<>(Type.class);
        messageClassMap.put(Type.MSG_CHAT_CREATE, ChatCreateMessage.class);
        messageClassMap.put(Type.MSG_CHAT_HIST, ChatHistMessage.class);
        messageClassMap.put(Type.MSG_CHAT_LIST, ChatListMessage.class);
        messageClassMap.put(Type.MSG_INFO, InfoMessage.class);
        messageClassMap.put(Type.MSG_LOGIN, LoginMessage.class);
        messageClassMap.put(Type.MSG_TEXT, TextMessage.class);
        messageClassMap.put(Type.MSG_CHAT_HIST_RESULT, ChatHistResultMessage.class);
        messageClassMap.put(Type.MSG_CHAT_LIST_RESULT, ChatListResultMessage.class);
        messageClassMap.put(Type.MSG_STATUS, StatusMessage.class);
        messageClassMap.put(Type.MSG_INFO_RESULT, InfoResultMessage.class);
    }

    public static Class<? extends Message> get(Type type) {
        return messageClassMap.get(type);
    }
}
