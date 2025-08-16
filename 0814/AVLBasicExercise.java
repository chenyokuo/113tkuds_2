public class AVLBasicExercise {

    static class Node {
        int data;
        Node left, right;
        int height;

        public Node(int data) {
            this.data = data;
            this.height = 1;
        }

        int getBalance() {
            int leftHeight = (left != null) ? left.height : 0;
            int rightHeight = (right != null) ? right.height : 0;
            return leftHeight - rightHeight;
        }

        void updateHeight() {
            int leftHeight = (left != null) ? left.height : 0;
            int rightHeight = (right != null) ? right.height : 0;
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }
    }

    private Node root;

    public void insert(int data) {
        root = insertRecursive(root, data);
    }

    private Node insertRecursive(Node node, int data) {
        if (node == null) return new Node(data);

        if (data < node.data) node.left = insertRecursive(node.left, data);
        else if (data > node.data) node.right = insertRecursive(node.right, data);
        else return node; // duplicate

        node.updateHeight();

        // 平衡修正
        int balance = node.getBalance();

        // LL
        if (balance > 1 && data < node.left.data)
            return rightRotate(node);

        // RR
        if (balance < -1 && data > node.right.data)
            return leftRotate(node);

        // LR
        if (balance > 1 && data > node.left.data) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
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

    public boolean search(int data) {
        return searchRecursive(root, data);
    }

    private boolean searchRecursive(Node node, int data) {
        if (node == null) return false;
        if (node.data == data) return true;
        if (data < node.data) return searchRecursive(node.left, data);
        else return searchRecursive(node.right, data);
    }

    public int getHeight() {
        return (root != null) ? root.height : 0;
    }

    public boolean isValidAVL() {
        return validateAVL(root) != -1;
    }

    private int validateAVL(Node node) {
        if (node == null) return 0;
        int lh = validateAVL(node.left);
        int rh = validateAVL(node.right);

        if (lh == -1 || rh == -1 || Math.abs(lh - rh) > 1)
            return -1;

        return Math.max(lh, rh) + 1;
    }

    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }

    public static void main(String[] args) {
        AVLBasicExercise tree = new AVLBasicExercise();

        System.out.println("== 插入節點 ==");
        int[] values = {30, 20, 40, 10, 25, 35, 50};
        for (int v : values) {
            tree.insert(v);
            tree.printTree();
        }

        System.out.println("== 搜尋 25 ==>");
        System.out.println(tree.search(25));

        System.out.println("== 樹的高度 ==>");
        System.out.println(tree.getHeight());

        System.out.println("== 是否為有效 AVL ==>");
        System.out.println(tree.isValidAVL());
    }
}
