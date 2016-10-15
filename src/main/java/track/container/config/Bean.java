package track.container.config;

import java.util.List;

/**
 * Представляет тег bean из конфига
 */
public class Bean {

    private String id; // Уникальный ID бина
    private String className; // Класс бина
    private List<Property> properties;

    public Bean() {
    }

    public Bean(String id, String className, List<Property> properties) {
        this.id = id;
        this.className = className;
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}
