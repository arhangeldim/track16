package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */

class Vertex {

    private Bean bean;

    public Vertex(Bean bean) {
        this.bean = bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }

    public Bean getBean() {
        return bean;
    }

    @Override
    public String toString() {
        return bean.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Vertex)) {
            return false;
        }
        Vertex ver = (Vertex) obj;
        return this.bean.getId().equals(ver.bean.getId());
    }

}

