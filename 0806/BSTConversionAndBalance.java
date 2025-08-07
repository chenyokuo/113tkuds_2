public class BSTConversionAndBalance {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // 1️⃣ BST → 排序的雙向鏈結串列
    static TreeNode prev = null;
    static TreeNode head = null;

    public static TreeNode bstToDoublyList(TreeNode root) {
        prev = null;
        head = null;
        inorderToList(root);
        return head;
    }

    private static void inorderToList(TreeNode node) {
        if (node == null) return;
        inorderToList(node.left);
        if (prev != null) {
            prev.right = node;
            node.left = prev;
        } else {
            head = node;
        }
        prev = node;
        inorderToList(node.right);
    }

    // 2️⃣ 排序陣列 → 平衡 BST
    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildBST(nums, 0, nums.length - 1);
    }

    private static TreeNode buildBST(int[] arr, int l, int r) {
        if (l > r) return null;
        int m = (l + r) / 2;
        TreeNode node = new TreeNode(arr[m]);
        node.left = buildBST(arr, l, m - 1);
        node.right = buildBST(arr, m + 1, r);
        return node;
    }

    // 3️⃣ 判斷是否平衡 & 印出平衡因子
    public static boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private static int checkBalance(TreeNode node) {
        if (node == null) return 0;
        int left = checkBalance(node.left);
        int right = checkBalance(node.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;

        int bf = right - left;
        System.out.println("節點 " + node.val + " 的平衡因子：" + bf);
        return Math.max(left, right) + 1;
    }

    // 4️⃣ 每個節點更新為「所有大於等於該節點的總和」
    public static void convertToGreaterTree(TreeNode root) {
        convertHelper(root, new int[]{0});
    }

    private static void convertHelper(TreeNode node, int[] sum) {
        if (node == null) return;
        convertHelper(node.right, sum);
        sum[0] += node.val;
        node.val = sum[0];
        convertHelper(node.left, sum);
    }

    // 輔助輸出：中序走訪
    public static void printInOrder(TreeNode node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.print(node.val + " ");
        printInOrder(node.right);
    }

    // 輔助輸出：雙向串列向右列印
    public static void printListRight(TreeNode head) {
        TreeNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // 建立 BST 測試用
        TreeNode bst = new TreeNode(5);
        bst.left = new TreeNode(3);
        bst.right = new TreeNode(8);
        bst.left.left = new TreeNode(2);
        bst.left.right = new TreeNode(4);
        bst.right.left = new TreeNode(7);
        bst.right.right = new TreeNode(10);

        System.out.println("=== 1️⃣ BST 轉為雙向串列（中序） ===");
        TreeNode listHead = bstToDoublyList(bst);
        printListRight(listHead);

        System.out.println("\n=== 2️⃣ 排序陣列轉為平衡 BST ===");
        int[] sorted = {1, 2, 3, 4, 5, 6, 7};
        TreeNode balanced = sortedArrayToBST(sorted);
        System.out.print("中序驗證：");
        printInOrder(balanced);
        System.out.println();

        System.out.println("\n=== 3️⃣ 檢查是否為平衡樹 + 平衡因子 ===");
        boolean isBal = isBalanced(balanced);
        System.out.println("結果：" + isBal);

        System.out.println("\n=== 4️⃣ 將 BST 節點轉為『大於等於總和』 ===");
        convertToGreaterTree(balanced);
        System.out.print("轉換後中序：");
        printInOrder(balanced);
        System.out.println();
    }
}
