import java.util.Arrays;

public class DijkstraAlgorithm {
    public static int[] dijkstraLeftist(Graph graph, int source) {
        int V = graph.getV();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        LeftistHeap heap = new LeftistHeap();
        heap.insert(source, 0);

        boolean[] visited = new boolean[V];

        while (!heap.isEmpty()) {
            LeftistNode min = heap.deleteMin();
            int u = min.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Graph.Edge edge : graph.getAdjList().get(u)) {
                int v = edge.dest;
                int weight = edge.weight;

                if (!visited[v] && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    heap.insert(v, dist[v]);
                }
            }
        }

        return dist;
    }

    public static int[] dijkstraFibonacci(Graph graph, int source) {
        int V = graph.getV();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        FibonacciHeap heap = new FibonacciHeap();
        heap.insert(source, 0);

        boolean[] visited = new boolean[V];

        while (!heap.isEmpty()) {
            FibonacciHeap.FibNode min = heap.extractMin();
            int u = min.vertex;

            if (visited[u]) continue;
            visited[u] = true;

            for (Graph.Edge edge : graph.getAdjList().get(u)) {
                int v = edge.dest;
                int weight = edge.weight;

                if (!visited[v] && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    heap.insert(v, dist[v]);
                }
            }
        }

        return dist;
    }
}