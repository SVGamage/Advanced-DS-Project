import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// FibonacciHeap.java
public class FibonacciHeap {
    private static class FibNode {
        int vertex;
        int key;
        int degree;
        boolean marked;
        FibNode parent;
        FibNode child;
        FibNode left;
        FibNode right;

        FibNode(int vertex, int key) {
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

    private FibNode min;
    private int size;
    private Map<Integer, FibNode> vertices;

    public FibonacciHeap() {
        min = null;
        size = 0;
        vertices = new HashMap<>();
    }

    public void insert(int vertex, int key) {
        FibNode node = new FibNode(vertex, key);
        vertices.put(vertex, node);

        if (min == null) {
            min = node;
        } else {
            concatenate(min, node);
            if (node.key < min.key) {
                min = node;
            }
        }
        size++;
    }

    private void concatenate(FibNode a, FibNode b) {
        FibNode temp = a.right;
        a.right = b.right;
        b.right.left = a;
        b.right = temp;
        temp.left = b;
    }

    public FibNode extractMin() {
        FibNode z = min;
        if (z != null) {
            if (z.child != null) {
                FibNode child = z.child;
                do {
                    FibNode next = child.right;
                    concatenate(min, child);
                    child.parent = null;
                    child = next;
                } while (child != z.child);
            }

            z.left.right = z.right;
            z.right.left = z.left;

            if (z == z.right) {
                min = null;
            } else {
                min = z.right;
                consolidate();
            }
            size--;
        }
        return z;
    }

    private void consolidate() {
        int maxDegree = (int) (Math.log(size) / Math.log(2)) + 1;
        FibNode[] A = new FibNode[maxDegree];

        List<FibNode> rootList = new ArrayList<>();
        FibNode current = min;
        do {
            rootList.add(current);
            current = current.right;
        } while (current != min);

        for (FibNode w : rootList) {
            FibNode x = w;
            int d = x.degree;

            while (A[d] != null) {
                FibNode y = A[d];
                if (x.key > y.key) {
                    FibNode temp = x;
                    x = y;
                    y = temp;
                }
                link(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
        }

        min = null;
        for (FibNode a : A) {
            if (a != null) {
                if (min == null) {
                    min = a;
                    a.left = a;
                    a.right = a;
                } else {
                    concatenate(min, a);
                    if (a.key < min.key) {
                        min = a;
                    }
                }
            }
        }
    }

    private void link(FibNode y, FibNode x) {
        y.left.right = y.right;
        y.right.left = y.left;

        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            concatenate(x.child, y);
        }

        y.parent = x;
        x.degree++;
        y.marked = false;
    }

    public void decreaseKey(int vertex, int newKey) {
        FibNode x = vertices.get(vertex);
        if (x == null || newKey >= x.key) return;

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

        concatenate(min, x);
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
}