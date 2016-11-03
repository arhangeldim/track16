package track.container.config;

import java.beans.Beans;
import java.util.List;

public class Root {
    private List<Bean> beans;

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }
}