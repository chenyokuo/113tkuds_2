import java.util.*;

public class BinaryTreeBasicOperations {

    // 二元樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 1️⃣ 計算總和與平均值
    static class SumResult {
        int sum;
        int count;
    }

    public static SumResult calculateSumAndCount(TreeNode root) {
        if (root == null) return new SumResult();

        SumResult left = calculateSumAndCount(root.left);
        SumResult right = calculateSumAndCount(root.right);

        SumResult result = new SumResult();
        result.sum = root.val + left.sum + right.sum;
        result.count = 1 + left.count + right.count;
        return result;
    }

    // 2️⃣ 最大值與最小值節點
    public static int findMax(TreeNode root) {
        if (root == null) return Integer.MIN_VALUE;
        return Math.max(root.val, Math.max(findMax(root.left), findMax(root.right)));
    }

    public static int findMin(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;
        return Math.min(root.val, Math.min(findMin(root.left), findMin(root.right)));
    }

    // 3️⃣ 計算樹的寬度（最大層節點數）
    public static int treeWidth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            maxWidth = Math.max(maxWidth, levelSize);
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return maxWidth;
    }

    // 4️⃣ 判斷是否為完全二元樹（Complete Binary Tree）
    public static boolean isCompleteBinaryTree(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean foundNull = false;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                foundNull = true;
            } else {
                if (foundNull) return false; // 遇到空後還有實體節點，不合法
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        return true;
    }

    // 建立測試樹
    public static TreeNode buildTestTree() {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(20);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(25);
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = buildTestTree();

        System.out.println("=== 1️⃣ 總和與平均 ===");
        SumResult result = calculateSumAndCount(root);
        System.out.println("總和：" + result.sum + "，平均值：" + ((double) result.sum / result.count));

        System.out.println("\n=== 2️⃣ 最大值與最小值節點 ===");
        System.out.println("最大值：" + findMax(root));
        System.out.println("最小值：" + findMin(root));

        System.out.println("\n=== 3️⃣ 樹的最大寬度（節點最多的一層） ===");
        System.out.println("寬度：" + treeWidth(root));

        System.out.println("\n=== 4️⃣ 是否為完全二元樹？ ===");
        System.out.println("結果：" + isCompleteBinaryTree(root));
    }
}
