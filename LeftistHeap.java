public class LeftistHeap {

    private LeftistNode root;

    public LeftistHeap() {
        root = null;
    }

    public void insert(int vertex, int key) {
        LeftistNode newNode = new LeftistNode(vertex, key);
        root = meld(root, newNode);
    }

    public LeftistNode deleteMin() {
        if (root == null) return null;
        LeftistNode minNode = root;
        root = meld(root.leftChild, root.rightChild);
        return minNode;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private LeftistNode meld(LeftistNode tree1, LeftistNode tree2) {
        if (tree1 == null) return tree2;
        if (tree2 == null) return tree1;

        if (tree1.key > tree2.key) {
            LeftistNode temp = tree1;
            tree1 = tree2;
            tree2 = temp;
        }

        tree1.rightChild = meld(tree1.rightChild, tree2);

        if (tree1.leftChild == null) {
            tree1.leftChild = tree1.rightChild;
            tree1.rightChild = null;
        } else {
            if (tree1.rightChild != null && tree1.leftChild.nullPathLength < tree1.rightChild.nullPathLength) {
                LeftistNode temp = tree1.leftChild;
                tree1.leftChild = tree1.rightChild;
                tree1.rightChild = temp;
            }
            tree1.nullPathLength = (tree1.rightChild != null ? tree1.rightChild.nullPathLength : 0) + 1;
        }

        return tree1;
    }

    public void decreaseKey(int vertex, int newKey) {
        delete(vertex);
        insert(vertex, newKey);
    }

    private void delete(int vertex) {
        root = delete(root, vertex);
    }

    private LeftistNode delete(LeftistNode currentRoot, int vertex) {
        if (currentRoot == null) return null;
        if (currentRoot.vertex == vertex) {
            return meld(currentRoot.leftChild, currentRoot.rightChild);
        } else {
            currentRoot.leftChild = delete(currentRoot.leftChild, vertex);
            currentRoot.rightChild = delete(currentRoot.rightChild, vertex);
            return currentRoot;
        }
    }

}