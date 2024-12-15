public class LeftistNode {
    int vertex;
    int key;
    int npl; // null path length
    LeftistNode left;
    LeftistNode right;

    public LeftistNode(int vertex, int key) {
        this.vertex = vertex;
        this.key = key;
        this.npl = 0;
        this.left = null;
        this.right = null;
    }
}