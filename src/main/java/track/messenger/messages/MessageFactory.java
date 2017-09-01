package track.messenger.messages;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.EnumMap;

import static javax.swing.UIManager.put;


/**
 * Created by ksushar on 1/26/17.
 */
public class MessageFactory {
    private static final ImmutableMap<Type, Class<? extends Message>> messageClassMap = ImmutableMap.of(
            Type.MSG_LOGIN, LoginMessage.class,
            Type.MSG_INFO, InfoMessage.class,
            Type.MSG_INFO_RESULT, InfoResultMessage.class,
            Type.MSG_STATUS, StatusMessage.class
    );

    public static Class<? extends Message> get(Type type) {
        return messageClassMap.get(type);
    }
}