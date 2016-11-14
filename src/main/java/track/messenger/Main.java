package track.messenger;

import track.messenger.commands.*;
import track.messenger.messages.Type;
import track.messenger.net.MessengerServer;
import track.messenger.store.MessageStore;
import track.messenger.store.UserStore;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class Main {

    public static Map<Type, Class> typeCommandMap = new HashMap<>();
    public static UserStore users = new UserStore("store.sqlite3");
    public static MessageStore messages = new MessageStore("store.sqlite3");

    public static void setTypeCommandMap() {
        typeCommandMap.put(Type.MSG_LOGIN, LoginCommand.class);
        typeCommandMap.put(Type.MSG_INFO, InfoCommand.class);
        typeCommandMap.put(Type.MSG_QUIT, QuitCommand.class);
        typeCommandMap.put(Type.MSG_TEXT, TextCommand.class);
    }

    public static void main(String[] args) throws Exception {
        setTypeCommandMap();

        MessengerServer server = new MessengerServer();
        server.setPort(19000);
        server.start();

    }

}
