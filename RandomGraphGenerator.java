import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGraphGenerator {
    private Random random;

    public RandomGraphGenerator() {
        this.random = new Random();
    }

    public Graph generateConnectedGraph(int n, double density) {
        Graph graph = new Graph(n);
        int maxEdges = n * (n - 1) / 2;
        int numEdges = (int) (density * maxEdges);

        Set<Pair<Integer, Integer>> edges = new HashSet<>();
        Random random = ThreadLocalRandom.current();

        while (edges.size() < numEdges) {
            int u = random.nextInt(n);
            int v = random.nextInt(n);
            int weight = random.nextInt(1000) + 1;
            if (u != v && !edges.contains(new Pair<>(u, v)) && !edges.contains(new Pair<>(v, u))) {
                graph.addEdge(u, v, weight);
                edges.add(new Pair<>(u, v));
            }
        }

        System.out.printf("Successfully generated a random graph with %d vertices and %d edges.\n", n, numEdges);
        return graph;
    }
}