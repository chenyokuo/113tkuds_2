import java.util.*;

public class TreePathProblems {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // 1️⃣ 找出所有從根節點到葉節點的路徑
    public static List<List<Integer>> allPaths(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        findPaths(root, new ArrayList<>(), result);
        return result;
    }

    private static void findPaths(TreeNode node, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;

        path.add(node.val);
        if (node.left == null && node.right == null) {
            result.add(new ArrayList<>(path));
        } else {
            findPaths(node.left, path, result);
            findPaths(node.right, path, result);
        }
        path.remove(path.size() - 1);
    }

    // 2️⃣ 判斷是否存在根到葉總和為目標值的路徑
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }

    // 3️⃣ 找出最大總和的根到葉路徑
    public static List<Integer> maxSumRootToLeaf(TreeNode root) {
        List<Integer> bestPath = new ArrayList<>();
        maxSumHelper(root, 0, new ArrayList<>(), new int[]{Integer.MIN_VALUE}, bestPath);
        return bestPath;
    }

    private static void maxSumHelper(TreeNode node, int sum, List<Integer> current, int[] max, List<Integer> best) {
        if (node == null) return;

        current.add(node.val);
        sum += node.val;

        if (node.left == null && node.right == null) {
            if (sum > max[0]) {
                max[0] = sum;
                best.clear();
                best.addAll(current);
            }
        } else {
            maxSumHelper(node.left, sum, current, max, best);
            maxSumHelper(node.right, sum, current, max, best);
        }
        current.remove(current.size() - 1);
    }

    // 4️⃣ 任意兩節點間的最大路徑總和（樹徑最大和）
    public static int maxPathSum(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathHelper(root, max);
        return max[0];
    }

    private static int maxPathHelper(TreeNode node, int[] max) {
        if (node == null) return 0;

        int left = Math.max(0, maxPathHelper(node.left, max));
        int right = Math.max(0, maxPathHelper(node.right, max));

        max[0] = Math.max(max[0], left + right + node.val);

        return Math.max(left, right) + node.val;
    }

    // 測試樹建立
    public static TreeNode buildTestTree() {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(7);
        root.right.right = new TreeNode(11);
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = buildTestTree();

        System.out.println("=== 1️⃣ 所有根到葉路徑 ===");
        List<List<Integer>> paths = allPaths(root);
        for (List<Integer> path : paths) {
            System.out.println(path);
        }

        System.out.println("\n=== 2️⃣ 是否有總和為 18 的根到葉路徑 ===");
        System.out.println("結果：" + hasPathSum(root, 18));  // 10 + 5 + 3

        System.out.println("\n=== 3️⃣ 最大總和根到葉路徑 ===");
        List<Integer> maxPath = maxSumRootToLeaf(root);
        System.out.println("路徑：" + maxPath);
        int total = maxPath.stream().mapToInt(Integer::intValue).sum();
        System.out.println("總和：" + total);

        System.out.println("\n=== 4️⃣ 任意節點間最大路徑總和（樹徑） ===");
        System.out.println("最大總和：" + maxPathSum(root));  // 3 + 5 + 10 + -3 + 11 = 26
    }
}
