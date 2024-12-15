public class LeftistNode {
    int vertex;
    int key;
    LeftistNode leftChild;
    LeftistNode rightChild;
    int nullPathLength;

    public LeftistNode(int vertex, int key) {
        this.vertex = vertex;
        this.key = key;
        this.leftChild = null;
        this.rightChild = null;
        this.nullPathLength = 0;
    }
}