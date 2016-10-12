package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */

import java.util.*;

class Bean {

    private String id;
    private String beanclass;
    private List<Property> properties;

    public void setId(String id) {
        this.id = id;
    }

    public void setClass(String beanclass) {
        this.beanclass = beanclass;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public String getBeanClass() {
        return beanclass;
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
        return this.id.equals(((Bean) obj).id);
    }

}

