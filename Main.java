import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("Random mode: java Main -r n d x");
            System.out.println("File mode (Leftist): java Main -l filename");
            System.out.println("File mode (Fibonacci): java Main -f filename");
            return;
        }

        try {
            String mode = args[0];
            int[] distances;

            switch (mode) {
                case "-r":
                    if (args.length != 4) {
                        System.out.println("Random mode requires 3 parameters: n d x");
                        return;
                    }
                    handleRandomMode(args);
                    break;

                case "-l":
                    if (args.length != 2) {
                        System.out.println("File mode requires filename parameter");
                        return;
                    }
                    handleFileMode(args[1], true);
                    break;

                case "-f":
                    if (args.length != 2) {
                        System.out.println("File mode requires filename parameter");
                        return;
                    }
                    handleFileMode(args[1], false);
                    break;

                default:
                    System.out.println("Invalid mode. Use -r, -l, or -f");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleRandomMode(String[] args) {
        int n = Integer.parseInt(args[1]);
        double density = Double.parseDouble(args[2]) / 100.0;
        int source = Integer.parseInt(args[3]);

        RandomGraphGenerator generator = new RandomGraphGenerator();
        Graph graph = generator.generateConnectedGraph(n, density);

        // Measure time for Leftist Heap
        long startLeftist = System.nanoTime();
        List<Integer> distancesLeftist = DijkstraAlgorithm.dijkstraLeftist(graph, source);
        long timeLeftist = System.nanoTime() - startLeftist;

        // Measure time for Fibonacci Heap
        long startFib = System.nanoTime();
        List<Integer> distancesFib = DijkstraAlgorithm.dijkstraFibonacci(graph, source);
        long timeFib = System.nanoTime() - startFib;

        System.out.println("Random Graph Results:");
        System.out.println("Vertices: " + n + ", Density: " + density + ", Source: " + source);
        System.out.printf("Leftist Heap Time: %.3f ms\n" , timeLeftist/1000000.0);
        System.out.printf("Fibonacci Heap Time: %.3f ms\n" , timeFib/1000000.0);
    }

    private static void handleFileMode(String filename, boolean useLeftist) throws IOException {
        Graph graph = FileHandler.readGraphFromFile(filename);
        int source = 0; // assuming source is always 0 in file mode

        List<Integer> distances;
        if (useLeftist) {
            distances = DijkstraAlgorithm.dijkstraLeftist(graph, source);
        } else {
            distances = DijkstraAlgorithm.dijkstraFibonacci(graph, source);
        }

        FileHandler.printResults(distances);
    }
}