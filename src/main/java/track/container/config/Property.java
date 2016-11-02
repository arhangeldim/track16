package track.container.config;

/**
 * Описание тега property в xml конфигурации.
 * Тег описывает поля определенного бина
 */
public class Property {
    private String name; // Имя поля
    private String value; // Значение поля
    private ValueType type; // Метка ссылочное значение или примитив

    public Property() {
    }

    public Property(String name, String value, ValueType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return value;
    }

    /**
     * Установить значение в поле и указать тип поля как значение
     */
    public void setVal(String value) {
        this.value = value;
        setType(ValueType.VAL);
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    /**
     * Установить ссылку в поле и указать тип поля как ссылку
     */
    public void setRef(String ref) {
        this.value = ref;
        setType(ValueType.REF);
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                '}';
    }
}