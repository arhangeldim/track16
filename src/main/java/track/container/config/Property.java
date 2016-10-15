package track.container.config;

/**
 * Описание тега property в xml конфигурации.
 * Тег описывает поля определенного бина
 */
public class Property {
    private String name; // Имя поля
    private String ref;
    private String val; // Значение поля

    public Property() {
    }

    public Property(String name, String ref, String val) {
        this.name = name;
        this.ref = ref;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", ref='" + ref + '\'' +
                '}';
    }
}