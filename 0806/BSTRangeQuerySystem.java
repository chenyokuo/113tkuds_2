import java.util.*;

public class BSTRangeQuerySystem {

    // 二元搜尋樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // 插入節點（建立 BST）
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else root.right = insert(root.right, val);
        return root;
    }

    // 1️⃣ 範圍查詢：列出所有值在 [min, max] 的節點
    public static void rangeQuery(TreeNode root, int min, int max, List<Integer> result) {
        if (root == null) return;

        if (root.val > min) rangeQuery(root.left, min, max, result);
        if (root.val >= min && root.val <= max) result.add(root.val);
        if (root.val < max) rangeQuery(root.right, min, max, result);
    }

    // 2️⃣ 範圍計數
    public static int rangeCount(TreeNode root, int min, int max) {
        if (root == null) return 0;
        if (root.val < min) return rangeCount(root.right, min, max);
        if (root.val > max) return rangeCount(root.left, min, max);
        return 1 + rangeCount(root.left, min, max) + rangeCount(root.right, min, max);
    }

    // 3️⃣ 範圍總和
    public static int rangeSum(TreeNode root, int min, int max) {
        if (root == null) return 0;
        if (root.val < min) return rangeSum(root.right, min, max);
        if (root.val > max) return rangeSum(root.left, min, max);
        return root.val + rangeSum(root.left, min, max) + rangeSum(root.right, min, max);
    }

    // 4️⃣ 最接近查詢
    public static int closestValue(TreeNode root, int target) {
        return closestHelper(root, target, root.val);
    }

    private static int closestHelper(TreeNode node, int target, int closest) {
        if (node == null) return closest;

        if (Math.abs(node.val - target) < Math.abs(closest - target)) {
            closest = node.val;
        }

        if (target < node.val) {
            return closestHelper(node.left, target, closest);
        } else if (target > node.val) {
            return closestHelper(node.right, target, closest);
        } else {
            return node.val;
        }
    }

    // 測試用 BST
    public static TreeNode buildTestBST() {
        int[] values = {15, 10, 20, 8, 12, 17, 25};
        TreeNode root = null;
        for (int val : values) {
            root = insert(root, val);
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = buildTestBST();

        int min = 10, max = 20;
        System.out.println("=== 1️⃣ 範圍查詢 [10,20] ===");
        List<Integer> inRange = new ArrayList<>();
        rangeQuery(root, min, max, inRange);
        System.out.println("節點值：" + inRange);

        System.out.println("\n=== 2️⃣ 範圍計數 [10,20] ===");
        System.out.println("節點數：" + rangeCount(root, min, max));

        System.out.println("\n=== 3️⃣ 範圍總和 [10,20] ===");
        System.out.println("總和：" + rangeSum(root, min, max));

        System.out.println("\n=== 4️⃣ 最接近值查詢 ===");
        int target = 13;
        System.out.println("目標值：" + target + "，最接近節點值：" + closestValue(root, target));
    }
}
