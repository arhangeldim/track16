package track.container.config;

import java.util.Map;

/**
 * Представляет тег bean из конфига
 */
public class Bean {

    private String id; // Уникальный ID бина
    private String className; // Класс бина

    /*
    Встроенная структура данных Java - ассоциативная таблица
    С помощью неё можно хранить данные типа Ключ-Значение
    доступны операции
    put(key, value) - поместить значение с заданным ключом
    get(key) - получить значение по ключу (или null, если не найдено)
     */
    private Map<String, Property> properties; // Набор полей бина ИмяПоля-Значение

    public Bean(String id, String className, Map<String, Property> properties) {
        this.id = id;
        this.className = className;
        this.properties = properties;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
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

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", className='" + className + '\'' +
                ", properties=" + properties +
                '}';
    }
}
