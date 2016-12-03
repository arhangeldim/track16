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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graph graph1 = (Graph) o;

        if (hasCircles != graph1.hasCircles) return false;
        if (currTime != graph1.currTime) return false;
        if (vertices != null ? !vertices.equals(graph1.vertices) : graph1.vertices != null) return false;
        if (graph != null ? !graph.equals(graph1.graph) : graph1.graph != null) return false;
        if (visited != null ? !visited.equals(graph1.visited) : graph1.visited != null) return false;
        return timeOut != null ? timeOut.equals(graph1.timeOut) : graph1.timeOut == null;

    }

    @Override
    public int hashCode() {
        int result = vertices != null ? vertices.hashCode() : 0;
        result = 31 * result + (graph != null ? graph.hashCode() : 0);
        result = 31 * result + (hasCircles ? 1 : 0);
        result = 31 * result + (visited != null ? visited.hashCode() : 0);
        result = 31 * result + (timeOut != null ? timeOut.hashCode() : 0);
        result = 31 * result + currTime;
        return result;
    }
/*
    Будем организовывать доступ к вершинам по строке id, а не по всему bean, т которого вершина создана.
    */

    private Map<String, Bean> vertices = new HashMap<>();
    private Map<String, List<String>> graph = new HashMap<>();

    private void addEdge(String from, String to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new ArrayList<>());
        }
        graph.get(from).add(to);
    }

    /*
    Расширим функцию добавления вершины в функцию добавления вершины и всех рёбер из неё.
     */

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
        assert hasCircles;
    }

    private boolean hasCircles = false; //индикатор зацикленности графа.
    private Map<String, Integer> visited; //индикаторы пройденных вершин.
    private Map<String, Integer> timeOut; //времена выхода из вершин внутри dfs.
    private int currTime;

    /*
    Проверяем на зацикленность следующим образом:
    Каждый раз запускаем dfs с новым числом, которым помечаем отмеченные.
    Если вдруг мы нашли вершину, которую уже отметили текущим числом, значит мы зациклимся.
     */

    private void dfs(String curr, Integer visitedFlag) {
        if (!visited.containsKey(curr)) {
            visited.put(curr, visitedFlag);
            graph.get(curr).forEach(x -> dfs(x, visitedFlag));
            currTime++;
            timeOut.put(curr, currTime);
        } else if (visitedFlag.equals(visited.get(curr))) {
            hasCircles = true;
        }
    }

    private void bfs() {

    }

    /*
    Топографически сортируем граф в порядке времени выхода из вершины в dfs (timeOut)
     */

    List<Bean> sort() {
        visited = new HashMap<>();
        timeOut = new HashMap<>();
        currTime = 0;
        List<Bean> result = new ArrayList<>(vertices.values());

        vertices.keySet().forEach(ver -> dfs(ver, ver.hashCode()));

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
