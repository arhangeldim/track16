package track.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leonshting on 21.10.16.
 */
public class Converter {
    static final Map<String, String> primitiveToWrapper;

    static {
        Map<String, String> initMap = new HashMap<String, String>();
        initMap.put("byte", Byte.class.getName());
        initMap.put("short", Short.class.getName());
        initMap.put("int", Integer.class.getName());
        initMap.put("long", Long.class.getName());
        initMap.put("float", Float.class.getName());
        initMap.put("double", Double.class.getName());
        initMap.put("char", Character.class.getName());
        initMap.put("boolean", Character.class.getName());
        primitiveToWrapper = Collections.unmodifiableMap(initMap);
    }

    static Object convert(String primitiveType, String value)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class klass = Class.forName(primitiveToWrapper.get(primitiveType));
        Constructor constructor = klass.getConstructor(String.class);
        Object toReturn = constructor.newInstance(value);
        return toReturn;
    }

}
