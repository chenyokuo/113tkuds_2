import java.util.*;

public class AVLRangeQueryExercise {
    static class Node {
        int data;
        Node left, right;
        int height;

        Node(int data) {
            this.data = data;
            this.height = 1;
        }

        void updateHeight() {
            int lh = (left != null) ? left.height : 0;
            int rh = (right != null) ? right.height : 0;
            this.height = Math.max(lh, rh) + 1;
        }

        int getBalance() {
            int lh = (left != null) ? left.height : 0;
            int rh = (right != null) ? right.height : 0;
            return lh - rh;
        }
    }

    private Node root;

    public void insert(int data) {
        root = insertNode(root, data);
    }

    private Node insertNode(Node node, int data) {
        if (node == null) return new Node(data);
        if (data < node.data) node.left = insertNode(node.left, data);
        else if (data > node.data) node.right = insertNode(node.right, data);
        else return node;

        node.updateHeight();
        int balance = node.getBalance();

        if (balance > 1 && data < node.left.data)
            return rightRotate(node);
        if (balance < -1 && data > node.right.data)
            return leftRotate(node);
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && data < node.right.data) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.updateHeight();
        x.updateHeight();
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.updateHeight();
        y.updateHeight();
        return y;
    }

    public List<Integer> rangeQuery(int min, int max) {
        List<Integer> result = new ArrayList<>();
        inOrderRange(root, min, max, result);
        return result;
    }

    private void inOrderRange(Node node, int min, int max, List<Integer> result) {
        if (node == null) return;
        if (min < node.data)
            inOrderRange(node.left, min, max, result);
        if (min <= node.data && node.data <= max)
            result.add(node.data);
        if (max > node.data)
            inOrderRange(node.right, min, max, result);
    }

    public static void main(String[] args) {
        AVLRangeQueryExercise tree = new AVLRangeQueryExercise();
        int[] values = {20, 10, 30, 5, 15, 25, 35};
        for (int v : values) {
            tree.insert(v);
        }

        System.out.println("Range [10, 30]: " + tree.rangeQuery(10, 30));
    }
}
