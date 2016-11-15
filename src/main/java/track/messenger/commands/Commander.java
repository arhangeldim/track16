package track.messenger.commands;

import track.messenger.messages.Type;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoolekom on 15.11.16.
 */
public class Commander {
    private Map<Type, Command> typeCommandMap;
    private UserStore users;
    private MessageStore messages;

    public void setUsers(UserStore users) {
        this.users = users;
        users.connect();
    }

    public void setMessages(MessageStore messages) {
        this.messages = messages;
        messages.connect();
    }

    public Commander() {
        typeCommandMap = new HashMap<>();
    }

    public void setTypeCommandMap() {
        typeCommandMap.put(Type.MSG_LOGIN, new LoginCommand(users));
        typeCommandMap.put(Type.MSG_INFO, new InfoCommand(users));
        typeCommandMap.put(Type.MSG_QUIT, new QuitCommand());
        typeCommandMap.put(Type.MSG_TEXT, new TextCommand());
    }

    public Map<Type, Command> getTypeCommandMap() {
        return typeCommandMap;
    }
}
