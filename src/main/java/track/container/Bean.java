package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */

import java.util.List;

class Bean {

    private String id;
    private String beanClass;
    private List<Property> properties;

    public void setId(String id) {
        this.id = id;
    }

    public void setClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Bean)) {
            return false;
        }
        Bean bean = (Bean) obj;
        return this.id.equals(bean.id);
    }

}

