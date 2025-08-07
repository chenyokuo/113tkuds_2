public class TreeMirrorAndSymmetry {

    // 樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1️⃣ 判斷是否為對稱樹
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null || t1.val != t2.val) return false;
        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    // 2️⃣ 將樹轉為鏡像（in-place）
    public static void mirror(TreeNode root) {
        if (root == null) return;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        mirror(root.left);
        mirror(root.right);
    }

    // 3️⃣ 比較兩棵樹是否互為鏡像
    public static boolean areMirrors(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null || a.val != b.val) return false;
        return areMirrors(a.left, b.right) && areMirrors(a.right, b.left);
    }

    // 4️⃣ 判斷 subtree 是否為 root 的子樹
    public static boolean isSubtree(TreeNode root, TreeNode subtree) {
        if (subtree == null) return true;
        if (root == null) return false;
        if (isSameTree(root, subtree)) return true;
        return isSubtree(root.left, subtree) || isSubtree(root.right, subtree);
    }

    private static boolean isSameTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null || a.val != b.val) return false;
        return isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
    }

    // 輔助：中序列印樹
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    public static void main(String[] args) {
        // 測試對稱樹
        TreeNode symmetricRoot = new TreeNode(1);
        symmetricRoot.left = new TreeNode(2);
        symmetricRoot.right = new TreeNode(2);
        symmetricRoot.left.left = new TreeNode(3);
        symmetricRoot.right.right = new TreeNode(3);

        System.out.println("=== 1️⃣ 是否為對稱樹 ===");
        System.out.println("結果：" + isSymmetric(symmetricRoot));  // true

        // 測試鏡像
        System.out.println("\n=== 2️⃣ 鏡像轉換後中序 ===");
        mirror(symmetricRoot);
        printInOrder(symmetricRoot);
        System.out.println();

        // 測試是否互為鏡像
        TreeNode a = new TreeNode(1);
        a.left = new TreeNode(2);
        a.right = new TreeNode(3);

        TreeNode b = new TreeNode(1);
        b.left = new TreeNode(3);
        b.right = new TreeNode(2);

        System.out.println("\n=== 3️⃣ 是否互為鏡像 ===");
        System.out.println("結果：" + areMirrors(a, b));  // true

        // 測試子樹包含
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);

        TreeNode sub = new TreeNode(5);
        sub.left = new TreeNode(3);
        sub.right = new TreeNode(7);

        System.out.println("\n=== 4️⃣ 是否為子樹 ===");
        System.out.println("結果：" + isSubtree(root, sub));  // true
    }
}
