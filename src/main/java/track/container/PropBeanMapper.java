package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import java.util.*;

class PropBeanMapper {

    private Map<String,Bean> map = new HashMap<>();

    public PropBeanMapper(List<Bean> beans) {

        TreeSet<Bean> beanSet = new TreeSet<Bean>((a,b) -> a.getId().compareTo(b.getId()));
        beanSet.addAll(beans);

        TreeSet<String> propSet = new TreeSet<String>();
        for (Bean beanIt : beans) {
            for (Property propIt : beanIt.getProperties()) {
                if (propIt.getReference() != null) {
                    propSet.add(propIt.getReference());
                }
            }
        }

        Iterator<Bean> beanIt = beanSet.iterator();
        Bean bean = null;
        for (String propIt : propSet) {

            if (beanIt.hasNext()) {
                bean = beanIt.next();
            } else {
                map = null;
                return;
            }

            while (!propIt.equals(bean.getId())) {
                if (beanIt.hasNext()) {
                    bean = beanIt.next();
                } else {
                    break;
                }
            }

            if (propIt.equals(bean.getId())) {
                map.put(propIt, bean);
            } else {
                map = null;
                return;
            }
        }

    }

    public Map<String, Bean> getMap() {
        return map;
    }

}

