public class AVLRotations {
    
    // 右旋操作
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // 執行旋轉
        x.right = y;
        y.left = T2;

        // 更新高度
        y.updateHeight();
        x.updateHeight();

        return x; // 新的根節點
    }

    // 左旋操作
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // 執行旋轉
        y.left = x;
        x.right = T2;

        // 更新高度
        x.updateHeight();
        y.updateHeight();

        return y; // 新的根節點
    }

    // 測試用 main 方法
    public static void main(String[] args) {
        // 建立不平衡的樹 (LL型: 右旋範例)
        AVLNode root = new AVLNode(30);
        root.left = new AVLNode(20);
        root.left.left = new AVLNode(10);

        // 更新高度
        root.left.left.updateHeight();
        root.left.updateHeight();
        root.updateHeight();

        System.out.println("== 原始樹狀況（未旋轉） ==");
        System.out.println("Root: " + root.data + ", Height: " + root.height + ", Balance: " + root.getBalance());
        System.out.println("Left: " + root.left.data + ", Height: " + root.left.height + ", Balance: " + root.left.getBalance());
        System.out.println("Left-Left: " + root.left.left.data + ", Height: " + root.left.left.height + ", Balance: " + root.left.left.getBalance());

        // 執行右旋
        AVLNode newRoot = rightRotate(root);

        System.out.println("\n== 執行右旋後 ==");
        System.out.println("New Root: " + newRoot.data + ", Height: " + newRoot.height + ", Balance: " + newRoot.getBalance());
        System.out.println("Left: " + (newRoot.left != null ? newRoot.left.data : "null"));
        System.out.println("Right: " + (newRoot.right != null ? newRoot.right.data : "null"));
    }
}
