package track.messenger.commands;

import track.messenger.messages.Type;

import java.util.EnumMap;

/**
 * Created on 12.01.2017.
 */
public class CommandFactory {
    private static EnumMap<Type, Command> commandMap;
    static {
        commandMap = new EnumMap<>(Type.class);
        commandMap.put(Type.MSG_CHAT_CREATE, new ChatCreateCommand());
        commandMap.put(Type.MSG_CHAT_HIST, new ChatHistCommand());
        commandMap.put(Type.MSG_CHAT_LIST, new ChatListCommand());
        commandMap.put(Type.MSG_INFO, new InfoCommand());
        commandMap.put(Type.MSG_LOGIN, new LoginCommand());
        commandMap.put(Type.MSG_TEXT, new TextCommand());
    }

    public static Command get(Type type) {
        return commandMap.get(type);
    }
}
