import java.io.*;
import java.util.*;

public class M08_BSTRangedSum {
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

        // 讀取 L R（可同一行）
        StringTokenizer st = new StringTokenizer(readNonEmptyLine(br));
        int L = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        Node root = buildTree(arr);
        long ans = rangeSumBST(root, L, R);
        System.out.println("Sum: " + ans);
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return "";
    }

    // 由層序（-1 為 null）建樹
    private static Node buildTree(int[] a) {
        if (a.length == 0 || a[0] == -1) return null;
        Node root = new Node(a[0]);
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < a.length) {
            Node cur = q.poll();

            if (i < a.length) { // 左
                if (a[i] != -1) {
                    cur.left = new Node(a[i]);
                    q.offer(cur.left);
                }
                i++;
            }
            if (i < a.length) { // 右
                if (a[i] != -1) {
                    cur.right = new Node(a[i]);
                    q.offer(cur.right);
                }
                i++;
            }
        }
        return root;
    }

    // 針對 BST 的剪枝 DFS
    private static long rangeSumBST(Node root, int L, int R) {
        if (root == null) return 0L;
        if (root.val < L) return rangeSumBST(root.right, L, R); // 全在右側
        if (root.val > R) return rangeSumBST(root.left, L, R);  // 全在左側
        // 介於 [L,R]：加上自己並走兩側
        return root.val + rangeSumBST(root.left, L, R) + rangeSumBST(root.right, L, R);
    }
}
