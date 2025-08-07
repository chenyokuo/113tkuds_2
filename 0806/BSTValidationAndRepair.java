import java.util.*;

public class BSTValidationAndRepair {

    // 樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // 1️⃣ 驗證一棵樹是否為有效 BST
    public static boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }

    private static boolean validate(TreeNode node, Integer min, Integer max) {
        if (node == null) return true;
        if ((min != null && node.val <= min) || (max != null && node.val >= max))
            return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    // 2️⃣ 找出 BST 中不符合規則的節點（使用中序遍歷）
    public static List<TreeNode> findViolations(TreeNode root) {
        List<TreeNode> violations = new ArrayList<>();
        findViolationsHelper(root, new TreeNode[]{null}, violations);
        return violations;
    }

    private static void findViolationsHelper(TreeNode node, TreeNode[] prev, List<TreeNode> violations) {
        if (node == null) return;
        findViolationsHelper(node.left, prev, violations);
        if (prev[0] != null && node.val <= prev[0].val)
            violations.add(node);
        prev[0] = node;
        findViolationsHelper(node.right, prev, violations);
    }

    // 3️⃣ 修復有兩個節點錯誤的 BST（交換值）
    public static void recoverBST(TreeNode root) {
        TreeNode[] nodes = new TreeNode[3]; // first, second, prev
        recoverHelper(root, nodes);
        if (nodes[0] != null && nodes[1] != null) {
            int tmp = nodes[0].val;
            nodes[0].val = nodes[1].val;
            nodes[1].val = tmp;
        }
    }

    private static void recoverHelper(TreeNode node, TreeNode[] nodes) {
        if (node == null) return;

        recoverHelper(node.left, nodes);

        if (nodes[2] != null && node.val < nodes[2].val) {
            if (nodes[0] == null) {
                nodes[0] = nodes[2]; // first wrong
                nodes[1] = node;     // second wrong
            } else {
                nodes[1] = node;     // second wrong again
            }
        }
        nodes[2] = node; // prev

        recoverHelper(node.right, nodes);
    }

    // 4️⃣ 計算最少移除多少節點才能成為有效 BST（轉換成中序 + 最長遞增子序列）
    public static int minNodesToRemoveForBST(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        collectInOrder(root, inorder);
        return inorder.size() - lengthOfLIS(inorder);
    }

    private static void collectInOrder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        collectInOrder(node.left, list);
        list.add(node.val);
        collectInOrder(node.right, list);
    }

    // LIS（Longest Increasing Subsequence）
    private static int lengthOfLIS(List<Integer> nums) {
        List<Integer> dp = new ArrayList<>();
        for (int num : nums) {
            int i = Collections.binarySearch(dp, num);
            if (i < 0) i = -(i + 1);
            if (i == dp.size()) dp.add(num);
            else dp.set(i, num);
        }
        return dp.size();
    }

    // 輔助：中序列印
    public static void printInOrder(TreeNode root) {
        if (root == null) return;
        printInOrder(root.left);
        System.out.print(root.val + " ");
        printInOrder(root.right);
    }

    public static void main(String[] args) {
        // 測試樹（不合法 BST，有兩個節點錯置）
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(15);  // 錯誤
        root.right = new TreeNode(5);  // 錯誤
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(20);

        System.out.println("=== 1️⃣ 驗證是否為 BST ===");
        System.out.println("結果：" + isValidBST(root)); // false

        System.out.println("\n=== 2️⃣ 找出不合法節點 ===");
        List<TreeNode> violations = findViolations(root);
        System.out.print("違規節點值：");
        for (TreeNode node : violations)
            System.out.print(node.val + " ");
        System.out.println();

        System.out.println("\n=== 3️⃣ 修復 BST 後 ===");
        recoverBST(root);
        System.out.println("修復後中序：");
        printInOrder(root);  // 應該為升序列
        System.out.println("\n合法？" + isValidBST(root)); // true

        System.out.println("\n=== 4️⃣ 計算最少需移除幾個節點才能合法 ===");
        TreeNode t = new TreeNode(10);
        t.left = new TreeNode(8);
        t.right = new TreeNode(15);
        t.left.right = new TreeNode(12); // 非 BST，因為 12 > 10 應該在右子樹
        t.right.left = new TreeNode(5);  // 非 BST，因為 5 < 10 應該在左子樹
        System.out.println("需移除節點數：" + minNodesToRemoveForBST(t)); // 預期 2
    }
}
