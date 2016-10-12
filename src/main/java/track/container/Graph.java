package track.container;

/**
 * Created by geoolekom on 12.10.16.
 */
import java.util.TreeMap;
import java.util.List;
import java.util.SortedMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

class Graph {

    private SortedMap<Vertex, List<Vertex>> vertices = new TreeMap<>((first,second) ->
            first.getBean().getId().compareTo(second.getBean().getId()));
    private List<Vertex> roots = new ArrayList<Vertex>();

    public Graph(List<Bean> beans) {

        PropBeanMapper propBeans = new PropBeanMapper(beans);
        Map<String, Bean> map = propBeans.getMap();
        List<Vertex> leafs = new ArrayList<Vertex>();

        if (map == null) {
            System.out.println("Mapping error!");
        }

        for (Bean beanIt : beans) {
            Vertex ver = addVertex(beanIt);

            for (Property propIt : beanIt.getProperties()) {
                Bean bean = map.get(propIt.getReference());
                if (bean != null) {
                    //  System.out.println(leafs);
                    Vertex child = addVertex(bean);
                    addEdge(ver, child);
                    leafs.add(child);
                }
            }
            roots.add(ver);
            //  System.out.println(vertices.get(ver));

        }

        for (Vertex it : leafs) {
            roots.remove(it);
        }
    }

    public Vertex addVertex(Bean bean) {
        Vertex newVertex = new Vertex(bean);
        if (vertices.get(newVertex) == null) {
            List<Vertex> childVertices = new ArrayList<Vertex>();
            vertices.put(newVertex, childVertices);
            return newVertex;
        } else {
            return newVertex;
        }


    }

    public void addEdge(Vertex from, Vertex to) {
        vertices.get(from).add(to);
    }

    public boolean isConnected(Vertex ver1, Vertex ver2) {
        return vertices.get(ver1).contains(ver2);
    }

    public List<Vertex> getLinked(Vertex ver) {
        return vertices.get(ver);
    }

    public List<Vertex> sort() {

        if (roots.size() == 0) {
            System.out.println("Cycle appeared!");
            return null;
        }

        Stack<Vertex> stack = new Stack<Vertex>();
        List<Vertex> ordered = new ArrayList<Vertex>();
        Vertex peek;
        SortedMap<Vertex, List<Vertex>> tempVertices = new TreeMap<>(vertices);
        SortedMap<Vertex, Integer> visited = new TreeMap<>((first,second) ->
                first.getBean().getId().compareTo(second.getBean().getId()));

        for (Vertex it : roots) {
            stack.push(it);
        }

        while (!stack.isEmpty() && (peek = stack.peek()) != null) {

            if (visited.get(peek) == null) {
                visited.put(peek, 1);
            } else if (visited.get(peek) == 2) {
                stack.pop();
                continue;
            }

            List<Vertex> childVertices = getLinked(peek);
            if (childVertices == null || childVertices.isEmpty()) {
                ordered.add(peek);
                visited.put(peek, 2);
                stack.pop();
            } else {
                for (Vertex it : childVertices) {
                    if (visited.get(it) == null) {
                        stack.push(it);
                    } else if (visited.get(it) == 1) {
                        System.out.println("Cycle appeared!");
                        return null;
                    }
                    vertices.put(peek, null);
                }
            }

        }

        vertices = tempVertices;
        return ordered;
    }

}
