package track.container.config;

/**
 * Описание тега property в xml конфигурации.
 * Тег описывает поля определенного бина
 */
public class Property {
    private String name; // Имя поля
    private String val; // Значение поля
    private String ref; // Метка ссылочное значение

    public Property() {
    }

    public Property(String name, String val, String ref) {
        this.name = name;
        this.val = val;
        this.ref = ref;
    }

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
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", ref=" + ref +
                '}';
    }
}