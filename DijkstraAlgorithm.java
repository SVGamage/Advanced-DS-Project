import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstraAlgorithm {
    private static final int INF = Integer.MAX_VALUE;
    public static List<Integer> dijkstraLeftist(Graph graph, int source) {
        List<List<Pair<Integer, Integer>>> adjacencyList = graph.getAdjacencyList();
        int numVertices = adjacencyList.size();

        LeftistHeap lt = new LeftistHeap();
        List<Integer> minDist = new ArrayList<>(Collections.nCopies(numVertices, INF));
        minDist.set(source, 0);

        for (int i = 0; i < numVertices; i++) {
            lt.insert(i, minDist.get(i));
        }

        while (!lt.isEmpty()) {
            LeftistNode minNode = lt.deleteMin();
            int u = minNode.vertex;

            for (Pair<Integer, Integer> edge : adjacencyList.get(u)) {
                int v = edge.getKey();
                int weight = edge.getValue();

                if (minDist.get(u) + weight < minDist.get(v)) {
                    minDist.set(v, minDist.get(u) + weight);
                    lt.decreaseKey(v, minDist.get(v));
                }
            }
        }
        return minDist;
    }

    public static  List<Integer> dijkstraFibonacci(Graph graph, int source) {
        List<List<Pair<Integer, Integer>>> adjacencyList = graph.getAdjacencyList();
        int numVertices = adjacencyList.size();

        FibonacciHeap fh = new FibonacciHeap();
        List<Integer> minDist = new ArrayList<>(Collections.nCopies(numVertices, INF));
        minDist.set(source, 0);

        List<FibonacciHeap.FibNode> nodes = new ArrayList<>(numVertices);

        for (int i = 0; i < numVertices; i++) {
            nodes.add(fh.insert(minDist.get(i), i));
        }

        while (!fh.isEmpty()) {
            FibonacciHeap.FibNode minNode = fh.extractMin();
            int u = minNode.value;

            for (Pair<Integer, Integer> edge : adjacencyList.get(u)) {
                int v = edge.getKey();
                int weight = edge.getValue();

                if (minDist.get(u) + weight < minDist.get(v)) {
                    minDist.set(v, minDist.get(u) + weight);
                    fh.decreaseKey(nodes.get(v), minDist.get(v));
                }
            }
        }
        return minDist;}
}