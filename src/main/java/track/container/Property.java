package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */

class Property {

    private String name;
    private String value;
    private String reference;

    public Property() {
        name = null;
        value = null;
        reference = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        if (reference == null) {
            return name;
        } else {
            return reference;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((Property) obj).name);
    }
}
