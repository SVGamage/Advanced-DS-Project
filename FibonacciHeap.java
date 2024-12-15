import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FibonacciHeap {
    private FibNode min;
    private int size;
    private Map<Integer, FibNode> vertices;

    public FibonacciHeap() {
        min = null;
        size = 0;
        vertices = new HashMap<>();
    }

    public static class FibNode {
        int vertex;
        int key;
        int degree;
        boolean marked;
        FibNode parent;
        FibNode child;
        FibNode left;
        FibNode right;

        public FibNode(int vertex, int key) {
            this.vertex = vertex;
            this.key = key;
            this.degree = 0;
            this.marked = false;
            this.parent = null;
            this.child = null;
            this.left = this;
            this.right = this;
        }
    }

    public void insert(int vertex, int key) {
        FibNode node = new FibNode(vertex, key);
        vertices.put(vertex, node);

        if (min == null) {
            min = node;
        } else {
            // Insert into root list
            node.right = min.right;
            node.left = min;
            min.right.left = node;
            min.right = node;

            if (node.key < min.key) {
                min = node;
            }
        }
        size++;
    }

    public FibNode extractMin() {
        FibNode z = min;
        if (z != null) {
            // Add all children to root list
            if (z.child != null) {
                FibNode child = z.child;
                do {
                    FibNode next = child.right;
                    // Add to root list
                    child.right = min.right;
                    child.left = min;
                    min.right.left = child;
                    min.right = child;

                    child.parent = null;
                    child = next;
                } while (child != z.child);
            }

            // Remove z from root list
            z.left.right = z.right;
            z.right.left = z.left;

            if (z == z.right) {
                min = null;
            } else {
                min = z.right;
                consolidate();
            }
            size--;
            vertices.remove(z.vertex);
        }
        return z;
    }

    private void consolidate() {
        int maxDegree = (int)(Math.log(size) / Math.log(2)) + 1;
        FibNode[] degreeTable = new FibNode[maxDegree];

        // Get list of all roots
        List<FibNode> rootList = new ArrayList<>();
        if (min != null) {
            FibNode current = min;
            do {
                rootList.add(current);
                current = current.right;
            } while (current != min);
        }

        // Consolidate trees of same degree
        for (FibNode root : rootList) {
            FibNode x = root;
            int degree = x.degree;

            while (degreeTable[degree] != null) {
                FibNode y = degreeTable[degree];
                if (x.key > y.key) {
                    FibNode temp = x;
                    x = y;
                    y = temp;
                }

                link(y, x);
                degreeTable[degree] = null;
                degree++;
            }
            degreeTable[degree] = x;
        }

        // Rebuild root list and find new min
        min = null;
        for (FibNode node : degreeTable) {
            if (node != null) {
                if (min == null) {
                    min = node;
                    node.left = node;
                    node.right = node;
                } else {
                    // Add to root list
                    node.right = min.right;
                    node.left = min;
                    min.right.left = node;
                    min.right = node;

                    if (node.key < min.key) {
                        min = node;
                    }
                }
            }
        }
    }

    private void link(FibNode y, FibNode x) {
        // Remove y from root list
        y.left.right = y.right;
        y.right.left = y.left;

        // Make y a child of x
        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.right = x.child.right;
            y.left = x.child;
            x.child.right.left = y;
            x.child.right = y;
        }

        y.parent = x;
        x.degree++;
        y.marked = false;
    }

    public void decreaseKey(int vertex, int newKey) {
        FibNode x = vertices.get(vertex);
        if (x == null || newKey >= x.key) {
            return;
        }

        x.key = newKey;
        FibNode y = x.parent;

        if (y != null && x.key < y.key) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.key < min.key) {
            min = x;
        }
    }

    private void cut(FibNode x, FibNode y) {
        // Remove x from child list of y
        if (x.right == x) {
            y.child = null;
        } else {
            x.right.left = x.left;
            x.left.right = x.right;
            if (y.child == x) {
                y.child = x.right;
            }
        }
        y.degree--;

        // Add x to root list
        x.right = min.right;
        x.left = min;
        min.right.left = x;
        min.right = x;

        x.parent = null;
        x.marked = false;
    }

    private void cascadingCut(FibNode y) {
        FibNode z = y.parent;
        if (z != null) {
            if (!y.marked) {
                y.marked = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    public boolean isEmpty() {
        return min == null;
    }

    public FibNode getMin() {
        return min;
    }
}