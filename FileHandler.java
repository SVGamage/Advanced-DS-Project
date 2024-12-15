import java.io.*;
import java.util.*;

public class FileHandler {
    public static Graph readGraphFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int source = Integer.parseInt(reader.readLine().trim());
            String[] nm = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            Graph graph = new Graph(n);

            for (int i = 0; i < m; i++) {
                String[] edge = reader.readLine().trim().split("\\s+");
                int v1 = Integer.parseInt(edge[0]);
                int v2 = Integer.parseInt(edge[1]);
                int cost = Integer.parseInt(edge[2]);
                graph.addEdge(v1, v2, cost);
            }

            return graph;
        }
    }

    public static void printResults(List<Integer> distances) {
        for (int distance : distances) {
            System.out.println(distance);
        }
    }
}