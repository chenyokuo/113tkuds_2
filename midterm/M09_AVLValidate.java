import java.io.*;
import java.util.*;

public class M09_AVLValidate {
    static class Node {
        int val;
        Node left, right;
        Node(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(readNonEmptyLine(br).trim());

        // 讀滿 n 個整數（可跨行）
        int[] arr = new int[n];
        int filled = 0;
        while (filled < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                arr[filled++] = Integer.parseInt(st.nextToken());
            }
        }

        Node root = buildTree(arr);

        // 1) 檢查 BST（嚴格 min < val < max）
        boolean bstOk = isBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        if (!bstOk) {
            System.out.println("Invalid BST");
            return;
        }

        // 2) 檢查 AVL（後序回傳高度，失衡回傳 NEG_INF）
        int height = checkAVLHeight(root);
        if (height == NEG_INF) {
            System.out.println("Invalid AVL");
        } else {
            System.out.println("Valid");
        }
    }

    // ---------- 建樹：層序 + -1 表 null ----------
    private static Node buildTree(int[] a) {
        if (a.length == 0 || a[0] == -1) return null;
        Node root = new Node(a[0]);
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < a.length) {
            Node cur = q.poll();
            // left
            if (i < a.length && a[i] != -1) {
                cur.left = new Node(a[i]);
                q.offer(cur.left);
            }
            i++;
            // right
            if (i < a.length && a[i] != -1) {
                cur.right = new Node(a[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // ---------- BST 驗證：帶上下界（嚴格） ----------
    private static boolean isBST(Node node, long low, long high) {
        if (node == null) return true;
        if (!(low < node.val && node.val < high)) return false;
        return isBST(node.left, low, node.val) && isBST(node.right, node.val, high);
    }

    // ---------- AVL 驗證：回傳高度；失衡回 NEG_INF ----------
    private static final int NEG_INF = Integer.MIN_VALUE / 2; // 明顯異常值
    private static int checkAVLHeight(Node node) {
        if (node == null) return -1; // 空樹高度定義為 -1（葉子為 0）
        int lh = checkAVLHeight(node.left);
        if (lh == NEG_INF) return NEG_INF;
        int rh = checkAVLHeight(node.right);
        if (rh == NEG_INF) return NEG_INF;

        if (Math.abs(lh - rh) > 1) return NEG_INF; // 失衡
        return Math.max(lh, rh) + 1;
    }

    // ---------- 工具 ----------
    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return "";
    }
}

/*
 * Time Complexity: O(n)
 * 說明：
 * 1) 建樹：層序陣列走一遍、每個位置處理一次 → O(n)。
 * 2) BST 驗證：每節點恰訪問一次，遞迴帶界限 → O(n)。
 * 3) AVL 驗證：後序計算高度並檢平衡，每節點計算一次 → O(n)。
 * 整體：O(n)；空間為 O(h)（遞迴深度，最壞 O(n)，平衡時 O(log n)）。
 */
