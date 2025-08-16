public class AVLRotationExercise {

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

    public static Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.updateHeight();
        x.updateHeight();

        return x;
    }

    public static Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.updateHeight();
        y.updateHeight();

        return y;
    }

    public static void print(Node node) {
        if (node == null) return;
        print(node.left);
        System.out.print(node.data + " ");
        print(node.right);
    }

    public static void main(String[] args) {
        // LL Rotation
        Node root1 = new Node(30);
        root1.left = new Node(20);
        root1.left.left = new Node(10);
        root1.updateHeight(); root1.left.updateHeight(); root1.left.left.updateHeight();
        root1 = rightRotate(root1);
        System.out.println("After LL Rotation:");
        print(root1);
        System.out.println();

        // RR Rotation
        Node root2 = new Node(10);
        root2.right = new Node(20);
        root2.right.right = new Node(30);
        root2.updateHeight(); root2.right.updateHeight(); root2.right.right.updateHeight();
        root2 = leftRotate(root2);
        System.out.println("After RR Rotation:");
        print(root2);
        System.out.println();

        // LR Rotation
        Node root3 = new Node(30);
        root3.left = new Node(10);
        root3.left.right = new Node(20);
        root3.left.updateHeight(); root3.left.right.updateHeight(); root3.updateHeight();
        root3.left = leftRotate(root3.left);
        root3 = rightRotate(root3);
        System.out.println("After LR Rotation:");
        print(root3);
        System.out.println();

        // RL Rotation
        Node root4 = new Node(10);
        root4.right = new Node(30);
        root4.right.left = new Node(20);
        root4.right.updateHeight(); root4.right.left.updateHeight(); root4.updateHeight();
        root4.right = rightRotate(root4.right);
        root4 = leftRotate(root4);
        System.out.println("After RL Rotation:");
        print(root4);
        System.out.println();
    }
}
