import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
    private final List<List<Pair<Integer, Integer>>> adjacencyList;

    public Graph(int numVertices) {
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        adjacencyList.get(u).add(new Pair<>(v, weight));
        adjacencyList.get(v).add(new Pair<>(u, weight));
    }

    public List<List<Pair<Integer, Integer>>> getAdjacencyList() {
        return adjacencyList;
    }

    public static boolean checkDistances(List<Integer> leftistDistances, List<Integer> fibDistances) {
        return leftistDistances.equals(fibDistances);
    }
}