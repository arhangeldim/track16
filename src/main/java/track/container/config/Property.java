package track.container.config;

/**
 * Описание тега property в xml конфигурации.
 * Тег описывает поля определенного бина
 */
public class Property {


    public Property() {
    }

    private String name; // Имя поля
    private String val; // Значение поля
    private ValueType type; // Метка ссылочное значение или примитив

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
        setType(ValueType.VAL);
    }

    public void setRef(String ref){
        this.val = ref;
        setType(ValueType.REF);
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", type=" + type +
                '}';
    }
}