import java.util.*;

public class LevelOrderTraversalVariations {

    // 樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // 1️⃣ 每層儲存在不同 List 中
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                level.add(cur.val);
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            result.add(level);
        }
        return result;
    }

    // 2️⃣ 之字形層序走訪
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (leftToRight) level.addLast(cur.val);
                else level.addFirst(cur.val);

                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }

    // 3️⃣ 只列印每層最後一個節點
    public static List<Integer> rightmostNodes(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            TreeNode last = null;
            for (int i = 0; i < size; i++) {
                last = q.poll();
                if (last.left != null) q.offer(last.left);
                if (last.right != null) q.offer(last.right);
            }
            result.add(last.val);
        }

        return result;
    }

    // 4️⃣ 垂直層序走訪
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));

        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            TreeNode node = p.node;
            int col = p.col;

            columnMap.computeIfAbsent(col, x -> new ArrayList<>()).add(node.val);

            if (node.left != null) queue.offer(new Pair(node.left, col - 1));
            if (node.right != null) queue.offer(new Pair(node.right, col + 1));
        }

        result.addAll(columnMap.values());
        return result;
    }

    // 輔助類別：存放節點與其對應 column
    static class Pair {
        TreeNode node;
        int col;
        Pair(TreeNode n, int c) {
            node = n;
            col = c;
        }
    }

    // 測試用樹
    public static TreeNode buildSampleTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        return root;
    }

    // 主程式
    public static void main(String[] args) {
        TreeNode root = buildSampleTree();

        System.out.println("=== 1️⃣ 每層儲存在不同 List ===");
        List<List<Integer>> levels = levelOrder(root);
        for (List<Integer> level : levels)
            System.out.println(level);

        System.out.println("\n=== 2️⃣ 之字形層序走訪 ===");
        List<List<Integer>> zigzag = zigzagLevelOrder(root);
        for (List<Integer> level : zigzag)
            System.out.println(level);

        System.out.println("\n=== 3️⃣ 每層最後一個節點 ===");
        System.out.println(rightmostNodes(root)); // [1, 3, 7]

        System.out.println("\n=== 4️⃣ 垂直層序走訪 ===");
        List<List<Integer>> vertical = verticalOrder(root);
        for (List<Integer> col : vertical)
            System.out.println(col);
    }
}
