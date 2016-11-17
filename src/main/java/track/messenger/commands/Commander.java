package track.messenger.commands;

import track.messenger.messages.Type;
import track.messenger.store.ChatRelationStore;
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
    private ChatRelationStore chatRelations;
    private String hashAlgorithm;

    public void setUsers(UserStore users) {
        this.users = users;
        users.connect();
    }

    public void setMessages(MessageStore messages) {
        this.messages = messages;
        messages.connect();
    }

    public void setChatRelations(ChatRelationStore chatRelations) {
        this.chatRelations = chatRelations;
        chatRelations.connect();
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public Commander() {
        typeCommandMap = new HashMap<>();
    }

    public void setTypeCommandMap() {
        typeCommandMap.put(Type.MSG_LOGIN, new LoginCommand(users, hashAlgorithm));
        typeCommandMap.put(Type.MSG_REGISTER, new RegisterCommand(users, hashAlgorithm));
        typeCommandMap.put(Type.MSG_QUIT, new QuitCommand());
        typeCommandMap.put(Type.MSG_INFO, new InfoCommand(users));
        typeCommandMap.put(Type.MSG_TEXT, new TextCommand(chatRelations, messages));
        typeCommandMap.put(Type.MSG_CHAT_CREATE, new ChatCreateCommand(chatRelations));
        typeCommandMap.put(Type.MSG_CHAT_HIST, new ChatHistCommand(chatRelations, messages, users));
        typeCommandMap.put(Type.MSG_CHAT_LIST, new ChatListCommand(chatRelations));
    }

    public Command get(Type type) {
        return typeCommandMap.get(type);
    }
}
