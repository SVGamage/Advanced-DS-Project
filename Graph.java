// Graph.java
import java.util.*;

public class Graph {
    private int V; // number of vertices
    private List<List<Edge>> adj; // adjacency list

    static class Edge {
        int dest;
        int weight;

        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    public Graph(int v) {
        V = v;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        adj.get(src).add(new Edge(dest, weight));
        adj.get(dest).add(new Edge(src, weight)); // since undirected graph
    }

    public List<List<Edge>> getAdjList() {
        return adj;
    }

    public int getV() {
        return V;
    }
}