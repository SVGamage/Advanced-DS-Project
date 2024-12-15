import java.util.*;

public class FibonacciHeap {
    public static class FibNode {
        int key;
        int value;
        int degree;
        boolean isChildCut;
        FibNode parent;
        FibNode child;
        FibNode left;
        FibNode right;

        FibNode(int key, int value) {
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

    private FibNode minimumNode;
    private int heapSize;

    public FibonacciHeap() {
        this.minimumNode = null;
        this.heapSize = 0;
    }

    public FibNode insert(int key, int value) {
        FibNode newNode = new FibNode(key, value);
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

    private void mergeWithRootList(FibNode newNode) {
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

    private void removeFromRootList(FibNode nodeToRemove) {
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

    private void removeFromChildList(FibNode parent, FibNode childToRemove) {
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

    private FibNode mergeWithChildList(FibNode childList, FibNode newNode) {
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

    public FibNode extractMin() {
        FibNode extractedMin = minimumNode;
        if (extractedMin != null) {
            if (extractedMin.child != null) {
                for (FibNode childNode : iterate(extractedMin.child)) {
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

    public void decreaseKey(FibNode node, int newKey) {
        if (newKey > node.key) {
            throw new IllegalArgumentException("Invalid operation: new key (" + newKey + ") cannot be greater than current key (" + node.key + ").");
        }

        node.key = newKey;
        FibNode parentNode = node.parent;

        if (parentNode != null && node.key < parentNode.key) {
            cut(node, parentNode);
            cascadingCut(parentNode);
        }

        if (node.key < minimumNode.key) {
            minimumNode = node;
        }
    }

    private void cut(FibNode nodeToCut, FibNode parentNode) {
        removeFromChildList(parentNode, nodeToCut);
        parentNode.degree--;
        mergeWithRootList(nodeToCut);
        nodeToCut.parent = null;
        nodeToCut.isChildCut = false;
    }

    private void cascadingCut(FibNode parentNode) {
        FibNode grandparentNode = parentNode.parent;
        if (grandparentNode != null) {
            if (!parentNode.isChildCut) {
                parentNode.isChildCut = true;
            } else {
                cut(parentNode, grandparentNode);
                cascadingCut(grandparentNode);
            }
        }
    }

    private List<FibNode> iterate(FibNode head) {
        List<FibNode> nodes = new ArrayList<>();
        if (head == null) return nodes;

        FibNode currentNode = head;
        do {
            nodes.add(currentNode);
            currentNode = currentNode.right;
        } while (currentNode != head);

        return nodes;
    }

    private void consolidate() {
        int maxDegree = (int) (Math.log(heapSize) / Math.log(2)) + 10;
        List<FibNode> nodeArray = new ArrayList<>(Collections.nCopies(maxDegree, null));

        List<FibNode> nodes = iterate(minimumNode);

        for (FibNode currentNode : nodes) {
            FibNode x = currentNode;
            int degree = x.degree;
            while (nodeArray.get(degree) != null) {
                FibNode y = nodeArray.get(degree);
                if (x.key > y.key) {
                    FibNode temp = x;
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
        for (FibNode node : nodeArray) {
            if (node != null) {
                if (minimumNode == null || node.key < minimumNode.key) {
                    minimumNode = node;
                }
            }
        }
    }

    private void link(FibNode childNode, FibNode parentNode) {
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