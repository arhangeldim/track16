package track.messenger.store;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by geoolekom on 19.11.16.
 */
public class StoreFactory {

    private Map<Class, AbstractStore> stores = new HashMap<>();
    private AbstractStore storeImpl;

    public StoreFactory() {}

    public void put(AbstractStore store) {
        stores.put(store.getDataClass(), store);
        store.connect();
    }

    public AbstractStore get(Class storeClass) {
        return stores.get(storeClass);
    }

    public void setStoreImpl(AbstractStore storeImpl) {
        put(storeImpl);
    }

    public void close() {
        stores.values().forEach(AbstractStore::close);
    }


}
