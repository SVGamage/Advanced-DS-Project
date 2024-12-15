public class LeftistHeap {
    private LeftistNode root;

    public LeftistHeap() {
        root = null;
    }

    public void insert(int vertex, int key) {
        LeftistNode node = new LeftistNode(vertex, key);
        root = merge(root, node);
    }

    public LeftistNode deleteMin() {
        if (root == null) return null;

        LeftistNode min = root;
        root = merge(root.left, root.right);
        return min;
    }

    private LeftistNode merge(LeftistNode h1, LeftistNode h2) {
        if (h1 == null) return h2;
        if (h2 == null) return h1;

        if (h2.key < h1.key) {
            LeftistNode temp = h1;
            h1 = h2;
            h2 = temp;
        }

        h1.right = merge(h1.right, h2);

        if (h1.left == null) {
            h1.left = h1.right;
            h1.right = null;
        } else {
            if (h1.left.npl < h1.right.npl) {
                LeftistNode temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
            h1.npl = h1.right.npl + 1;
        }

        return h1;
    }

    public boolean isEmpty() {
        return root == null;
    }
}