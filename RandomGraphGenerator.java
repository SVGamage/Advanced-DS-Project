import java.util.*;

public class RandomGraphGenerator {
    private Random random;

    public RandomGraphGenerator() {
        this.random = new Random();
    }

    public Graph generateConnectedGraph(int n, double density) {
        Graph graph = new Graph(n);
        int maxEdges = (n * (n - 1)) / 2;
        int targetEdges = (int) (maxEdges * density);

        // Generate initial spanning tree to ensure connectivity
        for (int i = 1; i < n; i++) {
            int cost = random.nextInt(1000) + 1;
            graph.addEdge(i - 1, i, cost);
            targetEdges--;
        }

        // Add remaining edges randomly
        Set<String> addedEdges = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            addedEdges.add(i + "," + (i + 1));
            addedEdges.add((i + 1) + "," + i);
        }

        while (targetEdges > 0) {
            int v1 = random.nextInt(n);
            int v2 = random.nextInt(n);

            if (v1 != v2 && !addedEdges.contains(v1 + "," + v2)) {
                int cost = random.nextInt(1000) + 1;
                graph.addEdge(v1, v2, cost);
                addedEdges.add(v1 + "," + v2);
                addedEdges.add(v2 + "," + v1);
                targetEdges--;
            }
        }

        return graph;
    }
}