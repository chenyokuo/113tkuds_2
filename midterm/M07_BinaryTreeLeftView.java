import java.io.*;
import java.util.*;

public class M07_BinaryTreeLeftView {
    static class Node {
        int val;
        Node left, right;
        Node(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Node root = buildTree(arr);
        List<Integer> leftView = getLeftView(root);

        StringBuilder sb = new StringBuilder("LeftView:");
        for (int x : leftView) {
            sb.append(' ').append(x);
        }
        System.out.println(sb.toString());
    }

    // 用層序 + -1=null 建立二元樹
    private static Node buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;
        Node root = new Node(arr[0]);
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < arr.length) {
            Node cur = q.poll();
            // left child
            if (i < arr.length && arr[i] != -1) {
                cur.left = new Node(arr[i]);
                q.offer(cur.left);
            }
            i++;
            // right child
            if (i < arr.length && arr[i] != -1) {
                cur.right = new Node(arr[i]);
                q.offer(cur.right);
            }
            i++;
        }
        return root;
    }

    // BFS 每層第一個就是左視圖
    private static List<Integer> getLeftView(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                if (i == 0) res.add(cur.val); // 每層第一個
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
        }
        return res;
    }
}

