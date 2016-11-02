package track.container;

import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

import java.util.*;

/**
 * Created by zerts on 02.11.16.
 */

class Graph {

    private class Vertex {
        String id;

        Vertex(Bean bean) {
            id = bean.getId();
        }

        @Override
        public String toString() {
            return "Vertex{" + id + '}';
        }

    }


    private Map<String, Bean> vertices = new HashMap<>();
    private Map<String, List<String>> graph = new HashMap<>();

    private void addEdge(String from, String to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new ArrayList<>());
        }
        graph.get(from).add(to);
    }

    private void addEdges(Bean from) {
        Collection<Property> properties = from.getProperties().values();
        properties.forEach(x -> {
            if (x.getType().equals(ValueType.REF)) {
                addEdge(from.getId(), x.getValue());
            }
        });
        if (!graph.containsKey(from.getId())) {
            graph.put(from.getId(), new ArrayList<>());
        }
    }

    public boolean isConnected(String v1, String v2) {
        return vertices.containsKey(v1) && graph.get(v1).contains(v2);
    }

    public List<String> getLinked(String vertex) {
        if (graph.containsKey(vertex)) {
            return graph.get(vertex);
        }
        return null;
    }

    Graph(List<Bean> beans) {
        beans.forEach(x -> vertices.put(x.getId(), x));
        beans.forEach(this::addEdges);
    }

    private Set<String> visited;
    private Map<String, Integer> timeOut;
    private int currTime;

    private void dfs(String curr) {
        if (!visited.contains(curr)) {
            visited.add(curr);
            graph.get(curr).forEach(this::dfs);
            currTime++;
            timeOut.put(curr, currTime);
        }
    }

    List<Bean> sort() {
        visited = new HashSet<>();
        timeOut = new HashMap<>();
        currTime = 0;
        List<Bean> result = new ArrayList<>(vertices.values());

        vertices.keySet().forEach(this::dfs);

        System.out.println(timeOut);
        result.sort(Comparator.comparing(e -> timeOut.get(e.getId())));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph{\n");
        graph.forEach((key, value) -> sb.append(key).append(" ").append(value.toString()).append("\n"));
        sb.append("}\n");
        return sb.toString();
    }
}
