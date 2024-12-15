import java.util.*;

public class FibonacciHeap {
    private FibonacciNode minimumNode;
    private int heapSize;

    public FibonacciHeap() {
        this.minimumNode = null;
        this.heapSize = 0;
    }

    public FibonacciNode insert(int key, int value) {
        FibonacciNode newNode = new FibonacciNode(key, value);
        if (minimumNode == null) {
            minimumNode = newNode;
        } else {
            mergeWithRootList(newNode);
            if (newNode.key < minimumNode.key) {
                minimumNode = newNode;
            }
        }
        heapSize++;
        return newNode;
    }

    private void mergeWithRootList(FibonacciNode newNode) {
        if (minimumNode == null) {
            minimumNode = newNode;
        } else {
            newNode.left = minimumNode;
            newNode.right = minimumNode.right;
            minimumNode.right.left = newNode;
            minimumNode.right = newNode;
            if (newNode.key < minimumNode.key) {
                minimumNode = newNode;
            }
        }
    }

    private void removeFromRootList(FibonacciNode nodeToRemove) {
        if (nodeToRemove.right == nodeToRemove) {
            minimumNode = null;
        } else {
            nodeToRemove.left.right = nodeToRemove.right;
            nodeToRemove.right.left = nodeToRemove.left;
            if (minimumNode == nodeToRemove) {
                minimumNode = nodeToRemove.right;
            }
        }
    }

    private void removeFromChildList(FibonacciNode parent, FibonacciNode childToRemove) {
        if (childToRemove.right == childToRemove) {
            parent.child = null;
        } else {
            if (parent.child == childToRemove) {
                parent.child = childToRemove.right;
            }
            childToRemove.left.right = childToRemove.right;
            childToRemove.right.left = childToRemove.left;
        }
        childToRemove.left = childToRemove.right = childToRemove;
    }

    private FibonacciNode mergeWithChildList(FibonacciNode childList, FibonacciNode newNode) {
        if (childList == null) {
            return newNode;
        } else {
            newNode.left = childList;
            newNode.right = childList.right;
            childList.right.left = newNode;
            childList.right = newNode;
        }
        return childList;
    }

    public FibonacciNode extractMin() {
        FibonacciNode extractedMin = minimumNode;
        if (extractedMin != null) {
            if (extractedMin.child != null) {
                for (FibonacciNode childNode : iterate(extractedMin.child)) {
                    mergeWithRootList(childNode);
                    childNode.parent = null;
                }
            }
            removeFromRootList(extractedMin);
            if (extractedMin == extractedMin.right) {
                minimumNode = null;
            } else {
                minimumNode = extractedMin.right;
                consolidate();
            }
            heapSize--;
        }
        return extractedMin;
    }

    public void decreaseKey(FibonacciNode node, int newKey) {
        if (newKey > node.key) {
            throw new IllegalArgumentException("Invalid operation: new key (" + newKey + ") cannot be greater than current key (" + node.key + ").");
        }

        node.key = newKey;
        FibonacciNode parentNode = node.parent;

        if (parentNode != null && node.key < parentNode.key) {
            cut(node, parentNode);
            cascadingCut(parentNode);
        }

        if (node.key < minimumNode.key) {
            minimumNode = node;
        }
    }

    private void cut(FibonacciNode nodeToCut, FibonacciNode parentNode) {
        removeFromChildList(parentNode, nodeToCut);
        parentNode.degree--;
        mergeWithRootList(nodeToCut);
        nodeToCut.parent = null;
        nodeToCut.isChildCut = false;
    }

    private void cascadingCut(FibonacciNode parentNode) {
        FibonacciNode grandparentNode = parentNode.parent;
        if (grandparentNode != null) {
            if (!parentNode.isChildCut) {
                parentNode.isChildCut = true;
            } else {
                cut(parentNode, grandparentNode);
                cascadingCut(grandparentNode);
            }
        }
    }

    private List<FibonacciNode> iterate(FibonacciNode head) {
        List<FibonacciNode> nodes = new ArrayList<>();
        if (head == null) return nodes;

        FibonacciNode currentNode = head;
        do {
            nodes.add(currentNode);
            currentNode = currentNode.right;
        } while (currentNode != head);

        return nodes;
    }

    private void consolidate() {
        int maxDegree = (int) (Math.log(heapSize) / Math.log(2)) + 10;
        List<FibonacciNode> nodeArray = new ArrayList<>(Collections.nCopies(maxDegree, null));

        List<FibonacciNode> nodes = iterate(minimumNode);

        for (FibonacciNode currentNode : nodes) {
            FibonacciNode x = currentNode;
            int degree = x.degree;
            while (nodeArray.get(degree) != null) {
                FibonacciNode y = nodeArray.get(degree);
                if (x.key > y.key) {
                    FibonacciNode temp = x;
                    x = y;
                    y = temp;
                }
                link(y, x);
                nodeArray.set(degree, null);
                degree++;
            }
            nodeArray.set(degree, x);
        }

        minimumNode = null;
        for (FibonacciNode node : nodeArray) {
            if (node != null) {
                if (minimumNode == null || node.key < minimumNode.key) {
                    minimumNode = node;
                }
            }
        }
    }

    private void link(FibonacciNode childNode, FibonacciNode parentNode) {
        removeFromRootList(childNode);
        childNode.left = childNode.right = childNode; // Isolate the child
        parentNode.child = mergeWithChildList(parentNode.child, childNode);
        childNode.parent = parentNode;
        parentNode.degree++;
        childNode.isChildCut = false;
    }

    public boolean isEmpty() {
        return minimumNode == null;
    }
}