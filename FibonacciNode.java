public class FibonacciNode {
    int key;
    int value;
    int degree;
    boolean isChildCut;
    FibonacciNode parent;
    FibonacciNode child;
    FibonacciNode left;
    FibonacciNode right;

    public FibonacciNode(int key, int value) {
        this.key = key;
        this.value = value;
        this.degree = 0;
        this.isChildCut = false;
        this.parent = null;
        this.child = null;
        this.left = this;
        this.right = this;
    }
}
