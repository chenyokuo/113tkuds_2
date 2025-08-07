import java.util.*;

public class TreeReconstruction {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // 1️⃣ 前序 + 中序 → 重建樹
    public static TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = buildIndexMap(inorder);
        return buildPreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPreIn(int[] pre, int ps, int pe, int[] in, int is, int ie, Map<Integer, Integer> map) {
        if (ps > pe || is > ie) return null;
        TreeNode root = new TreeNode(pre[ps]);
        int inRoot = map.get(pre[ps]);
        int leftSize = inRoot - is;
        root.left = buildPreIn(pre, ps + 1, ps + leftSize, in, is, inRoot - 1, map);
        root.right = buildPreIn(pre, ps + leftSize + 1, pe, in, inRoot + 1, ie, map);
        return root;
    }

    // 2️⃣ 後序 + 中序 → 重建樹
    public static TreeNode buildFromPostIn(int[] postorder, int[] inorder) {
        Map<Integer, Integer> inMap = buildIndexMap(inorder);
        return buildPostIn(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1, inMap);
    }

    private static TreeNode buildPostIn(int[] post, int ps, int pe, int[] in, int is, int ie, Map<Integer, Integer> map) {
        if (ps > pe || is > ie) return null;
        TreeNode root = new TreeNode(post[pe]);
        int inRoot = map.get(post[pe]);
        int leftSize = inRoot - is;
        root.left = buildPostIn(post, ps, ps + leftSize - 1, in, is, inRoot - 1, map);
        root.right = buildPostIn(post, ps + leftSize, pe - 1, in, inRoot + 1, ie, map);
        return root;
    }

    // 3️⃣ 層序 → 完全二元樹重建
    public static TreeNode buildCompleteFromLevel(int[] levelOrder) {
        if (levelOrder.length == 0) return null;
        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;
        while (i < levelOrder.length) {
            TreeNode cur = queue.poll();
            if (i < levelOrder.length) {
                cur.left = new TreeNode(levelOrder[i++]);
                queue.offer(cur.left);
            }
            if (i < levelOrder.length) {
                cur.right = new TreeNode(levelOrder[i++]);
                queue.offer(cur.right);
            }
        }
        return root;
    }

    // 建立中序值→索引映射
    private static Map<Integer, Integer> buildIndexMap(int[] inorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            map.put(inorder[i], i);
        return map;
    }

    // 驗證用：中序輸出
    public static void printInorder(TreeNode root) {
        if (root == null) return;
        printInorder(root.left);
        System.out.print(root.val + " ");
        printInorder(root.right);
    }

    // 驗證用：前序輸出
    public static void printPreorder(TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        printPreorder(root.left);
        printPreorder(root.right);
    }

    // 驗證用：層序輸出
    public static void printLevelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            System.out.print(cur.val + " ");
            if (cur.left != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
        }
    }

    public static void main(String[] args) {
        // 測試資料
        int[] preorder = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder =  {4, 2, 5, 1, 6, 3, 7};
        int[] postorder = {4, 5, 2, 6, 7, 3, 1};
        int[] levelOrder = {1, 2, 3, 4, 5, 6, 7};

        System.out.println("=== 1️⃣ 前序 + 中序 重建 ===");
        TreeNode preInTree = buildFromPreIn(preorder, inorder);
        System.out.print("中序驗證："); printInorder(preInTree); System.out.println();
        System.out.print("前序驗證："); printPreorder(preInTree); System.out.println();

        System.out.println("\n=== 2️⃣ 後序 + 中序 重建 ===");
        TreeNode postInTree = buildFromPostIn(postorder, inorder);
        System.out.print("中序驗證："); printInorder(postInTree); System.out.println();
        System.out.print("前序驗證："); printPreorder(postInTree); System.out.println();

        System.out.println("\n=== 3️⃣ 層序重建完全二元樹 ===");
        TreeNode completeTree = buildCompleteFromLevel(levelOrder);
        System.out.print("層序驗證："); printLevelOrder(completeTree); System.out.println();
        System.out.print("中序驗證："); printInorder(completeTree); System.out.println();
    }
}
