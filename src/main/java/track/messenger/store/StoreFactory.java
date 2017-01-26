package track.messenger.store;

import java.util.EnumMap;

/**
 * Created on 29.12.2016.
 */
public class StoreFactory {
    private EnumMap<Type, BaseStore> storeMap;

    public StoreFactory(MessageStore messageStore, UserStore userStore) {
        storeMap = new EnumMap<>(Type.class);
        storeMap.put(Type.MESSAGE_STORE, messageStore);
        storeMap.put(Type.USER_STORE, userStore);
    }

    public BaseStore get(Type type) {
        return storeMap.get(type);
    }
}
