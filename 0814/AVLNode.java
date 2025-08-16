public class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }
    
    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }
    
    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }
    public static void main(String[] args) {
        // 手動建立節點
        AVLNode root = new AVLNode(30);
        root.left = new AVLNode(20);
        root.right = new AVLNode(40);
        root.left.left = new AVLNode(10);

        // 更新所有節點高度（從底層往上）
        root.left.left.updateHeight();  // 葉節點
        root.left.updateHeight();       // 有一個子節點
        root.right.updateHeight();      // 沒有子節點
        root.updateHeight();            // 有左右子樹

        // 顯示各節點資訊
        System.out.println("Root: " + root.data + ", Height: " + root.height + ", Balance: " + root.getBalance());
        System.out.println("Left: " + root.left.data + ", Height: " + root.left.height + ", Balance: " + root.left.getBalance());
        System.out.println("Right: " + root.right.data + ", Height: " + root.right.height + ", Balance: " + root.right.getBalance());
        System.out.println("Left-Left: " + root.left.left.data + ", Height: " + root.left.left.height + ", Balance: " + root.left.left.getBalance());
    }
}
