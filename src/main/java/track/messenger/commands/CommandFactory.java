package track.messenger.commands;

import track.messenger.messages.TextMessage;
import track.messenger.messages.Type;
import track.messenger.store.ChatRelationStore;
import track.messenger.store.MessageStore;
import track.messenger.store.StoreFactory;
import track.messenger.store.UserStore;
import track.messenger.store.dao.ChatRelation;
import track.messenger.store.dao.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoolekom on 15.11.16.
 */
public class CommandFactory {
    private Map<Type, Command> typeCommandMap;

    public CommandFactory() {
        typeCommandMap = new HashMap<>();
        typeCommandMap.put(Type.MSG_LOGIN, new LoginCommand());
        typeCommandMap.put(Type.MSG_REGISTER, new RegisterCommand());
        typeCommandMap.put(Type.MSG_QUIT, new QuitCommand());
        typeCommandMap.put(Type.MSG_INFO, new InfoCommand());
        typeCommandMap.put(Type.MSG_TEXT, new TextCommand());
        typeCommandMap.put(Type.MSG_CHAT_CREATE, new ChatCreateCommand());
        typeCommandMap.put(Type.MSG_CHAT_HIST, new ChatHistCommand());
        typeCommandMap.put(Type.MSG_CHAT_LIST, new ChatListCommand());
        typeCommandMap.put(Type.REFRESH, new RefreshCommand());
    }

    public Command get(Type type) {
        return typeCommandMap.get(type);
    }
}
