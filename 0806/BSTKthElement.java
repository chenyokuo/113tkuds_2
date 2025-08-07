import java.util.*;

public class BSTKthElement {

    // Augmented BST 節點（包含節點數量 size）
    static class TreeNode {
        int val;
        int size;  // 子樹大小（含自己）
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
            this.size = 1;
        }
    }

    // 動態插入（同時更新 size）
    public static TreeNode insert(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else root.right = insert(root.right, val);
        updateSize(root);
        return root;
    }

    // 動態刪除（BST 基本刪除 + 更新 size）
    public static TreeNode delete(TreeNode root, int val) {
        if (root == null) return null;

        if (val < root.val) {
            root.left = delete(root.left, val);
        } else if (val > root.val) {
            root.right = delete(root.right, val);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            TreeNode minLarger = findMin(root.right);
            root.val = minLarger.val;
            root.right = delete(root.right, minLarger.val);
        }
        updateSize(root);
        return root;
    }

    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private static void updateSize(TreeNode node) {
        node.size = 1 + getSize(node.left) + getSize(node.right);
    }

    private static int getSize(TreeNode node) {
        return (node == null) ? 0 : node.size;
    }

    // 1️⃣ 第 k 小元素
    public static int kthSmallest(TreeNode root, int k) {
        if (root == null) return -1;
        int leftSize = getSize(root.left);
        if (k == leftSize + 1) return root.val;
        if (k <= leftSize) return kthSmallest(root.left, k);
        return kthSmallest(root.right, k - leftSize - 1);
    }

    // 2️⃣ 第 k 大元素
    public static int kthLargest(TreeNode root, int k) {
        if (root == null) return -1;
        int rightSize = getSize(root.right);
        if (k == rightSize + 1) return root.val;
        if (k <= rightSize) return kthLargest(root.right, k);
        return kthLargest(root.left, k - rightSize - 1);
    }

    // 3️⃣ 找出第 k 小 ~ 第 j 小的所有元素
    public static List<Integer> rangeKth(TreeNode root, int k, int j) {
        List<Integer> result = new ArrayList<>();
        inorderRange(root, result, new int[]{0}, k, j);
        return result;
    }

    private static void inorderRange(TreeNode node, List<Integer> result, int[] count, int k, int j) {
        if (node == null) return;
        inorderRange(node.left, result, count, k, j);
        count[0]++;
        if (count[0] >= k && count[0] <= j) {
            result.add(node.val);
        }
        inorderRange(node.right, result, count, k, j);
    }

    // 主程式測試
    public static void main(String[] args) {
        int[] values = {20, 10, 30, 5, 15, 25, 35};
        TreeNode root = null;
        for (int val : values) {
            root = insert(root, val);
        }

        System.out.println("=== 1️⃣ 第 k 小元素 ===");
        System.out.println("第 3 小：" + kthSmallest(root, 3));  // 15

        System.out.println("\n=== 2️⃣ 第 k 大元素 ===");
        System.out.println("第 2 大：" + kthLargest(root, 2));  // 30

        System.out.println("\n=== 3️⃣ 第 k 小 ~ 第 j 小 ===");
        List<Integer> range = rangeKth(root, 2, 5);
        System.out.println("第 2~5 小元素：" + range);  // [10, 15, 20, 25]

        System.out.println("\n=== 4️⃣ 動態刪除並查詢 ===");
        root = delete(root, 15);  // 刪除節點 15
        System.out.println("刪除 15 後，第 3 小：" + kthSmallest(root, 3));  // 原本是 15，現在變成 20
    }
}
