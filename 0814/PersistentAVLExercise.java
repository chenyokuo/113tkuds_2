import java.util.*;

public class PersistentAVLExercise {
    // 不可變節點定義
    static class Node {
        final int key;
        final int height;
        final int size;
        final Node left, right;

        Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(height(left), height(right));
            this.size = 1 + size(left) + size(right);
        }

        static int height(Node node) {
            return node == null ? 0 : node.height;
        }

        static int size(Node node) {
            return node == null ? 0 : node.size;
        }

        int balanceFactor() {
            return height(left) - height(right);
        }
    }

    private final List<Node> versions = new ArrayList<>();

    public PersistentAVLExercise() {
        versions.add(null); // version 0: empty tree
    }

    public int currentVersion() {
        return versions.size() - 1;
    }

    public void insert(int version, int key) {
        Node root = insert(versions.get(version), key);
        versions.add(root); // New version created
    }

    public boolean search(int version, int key) {
        Node node = versions.get(version);
        while (node != null) {
            if (key < node.key) node = node.left;
            else if (key > node.key) node = node.right;
            else return true;
        }
        return false;
    }

    public List<Integer> inOrder(int version) {
        List<Integer> result = new ArrayList<>();
        inOrder(versions.get(version), result);
        return result;
    }

    private void inOrder(Node node, List<Integer> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.key);
        inOrder(node.right, result);
    }

    // === 插入（回傳新根） ===
    private Node insert(Node node, int key) {
        if (node == null) return new Node(key, null, null);
        if (key < node.key) {
            return balance(new Node(node.key, insert(node.left, key), node.right));
        } else if (key > node.key) {
            return balance(new Node(node.key, node.left, insert(node.right, key)));
        } else {
            // Duplicate keys are ignored
            return node;
        }
    }

    // === 平衡 AVL ===
    private Node balance(Node node) {
        int bf = node.balanceFactor();
        if (bf > 1) {
            if (node.left.balanceFactor() < 0) {
                return rotateRight(new Node(node.key, rotateLeft(node.left), node.right));
            }
            return rotateRight(node);
        } else if (bf < -1) {
            if (node.right.balanceFactor() > 0) {
                return rotateLeft(new Node(node.key, node.left, rotateRight(node.right)));
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        return new Node(x.key, x.left, new Node(y.key, T2, y.right));
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        return new Node(y.key, new Node(x.key, x.left, T2), y.right);
    }

    // === 測試 ===
    public static void main(String[] args) {
        PersistentAVLExercise tree = new PersistentAVLExercise();

        tree.insert(0, 10); // version 1
        tree.insert(1, 20); // version 2
        tree.insert(2, 5);  // version 3

        System.out.println("Version 1 in-order: " + tree.inOrder(1)); // [10]
        System.out.println("Version 2 in-order: " + tree.inOrder(2)); // [10, 20]
        System.out.println("Version 3 in-order: " + tree.inOrder(3)); // [5, 10, 20]

        System.out.println("Search 10 in version 1: " + tree.search(1, 10)); // true
        System.out.println("Search 20 in version 1: " + tree.search(1, 20)); // false
    }
}
