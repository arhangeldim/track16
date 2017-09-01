package track.messenger.commands;

import com.google.common.collect.ImmutableMap;
import track.messenger.messages.Type;

import java.util.EnumMap;

/**
 * Created by ksushar on 1/26/17.
 */
public class CommandFactory {
    private static final ImmutableMap<Type, Command> commandMap = ImmutableMap.of(
            Type.MSG_LOGIN, new LoginCommand(),
            Type.MSG_INFO, new InfoCommand(),
            Type.MSG_TEXT, new TextCommand()
    );

    public static Command get(Type type) {
        return commandMap.get(type);
    }
}
