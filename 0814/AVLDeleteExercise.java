public class AVLDeleteExercise {

    class Node {
        int key, height;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1;
        }
    }

    private Node root;

    // 插入節點（供測試）
    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node; // 不允許重複值

        updateHeight(node);
        return rebalance(node);
    }

    // 刪除節點（外部調用）
    public void delete(int key) {
        root = delete(root, key);
    }

    // 真正的刪除邏輯
    private Node delete(Node node, int key) {
        if (node == null)
            return null;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // 節點找到，要刪除
            if (node.left == null || node.right == null) {
                // 只有一個子節點或是葉節點
                node = (node.left != null) ? node.left : node.right;
            } else {
                // 有兩個子節點：找後繼（右子樹最小值）
                Node temp = findMin(node.right);
                node.key = temp.key;
                node.right = delete(node.right, temp.key);
            }
        }

        if (node == null)
            return null;

        updateHeight(node);
        return rebalance(node);
    }

    // 更新節點高度
    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    // 計算高度
    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    // 平衡因子
    private int getBalance(Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // AVL 重平衡邏輯
    private Node rebalance(Node node) {
        int balance = getBalance(node);

        // 左重
        if (balance > 1) {
            if (getBalance(node.left) >= 0) {
                return rotateRight(node); // LL
            } else {
                node.left = rotateLeft(node.left); // LR
                return rotateRight(node);
            }
        }

        // 右重
        if (balance < -1) {
            if (getBalance(node.right) <= 0) {
                return rotateLeft(node); // RR
            } else {
                node.right = rotateRight(node.right); // RL
                return rotateLeft(node);
            }
        }

        return node; // 已平衡
    }

    // 右旋
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // 左旋
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // 找最小值（後繼用）
    private Node findMin(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // 中序走訪
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    // 主程式：測試 AVL 刪除操作
    public static void main(String[] args) {
        AVLDeleteExercise tree = new AVLDeleteExercise();

        int[] keys = { 20, 10, 30, 5, 15, 25, 35 };
        for (int key : keys)
            tree.insert(key);

        System.out.println("中序走訪（刪除前）：");
        tree.inorder();

        System.out.println("刪除葉節點 5");
        tree.delete(5);
        tree.inorder();

        System.out.println("刪除只有一個子節點的節點 35");
        tree.delete(35);
        tree.inorder();

        System.out.println("刪除有兩個子節點的節點 20");
        tree.delete(20);
        tree.inorder();
    }
}
