// 題目：合併兩院區掛號清單
// 給定兩條已升序排序的單向鏈結串列，請將它們合併為一條同樣升序的新串列，並輸出結果。

import java.io.*;
import java.util.*;

public class LC21_MergeTwoLists_Clinics {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        Integer mObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);
        int m = (mObj == null ? 0 : mObj);

        ListNode head1 = null, tail1 = null;
        for (int i = 0; i < n; i++) {
            ListNode node = new ListNode(in.nextLong());
            if (head1 == null) {
                head1 = tail1 = node;
            } else {
                tail1.next = node;
                tail1 = node;
            }
        }

        ListNode head2 = null, tail2 = null;
        for (int i = 0; i < m; i++) {
            ListNode node = new ListNode(in.nextLong());
            if (head2 == null) {
                head2 = tail2 = node;
            } else {
                tail2.next = node;
                tail2 = node;
            }
        }

        ListNode merged = mergeTwoLists(head1, head2);

        // 輸出合併後序列（空串則不輸出）
        StringBuilder sb = new StringBuilder();
        for (ListNode p = merged; p != null; p = p.next) {
            sb.append(p.val);
            if (p.next != null) {
                sb.append(' ');
            }
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
    }

    // 兩指針合併：Dummy + tail
    private static ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b; // 掛上剩餘
        return dummy.next;
    }

    // 節點定義（使用 long 以涵蓋 ±1e9）
    private static class ListNode {

        long val;
        ListNode next;

        ListNode(long v) {
            this.val = v;
        }
    }

    // 輕量快速輸入
    private static class FastIn {

        private final BufferedReader br;
        private StringTokenizer st;

        FastIn(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return null;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        Integer nextInt() throws IOException {
            String t = next();
            if (t == null) {
                return null;
            }
            return Integer.parseInt(t);
        }

        Long nextLong() throws IOException {
            String t = next();
            if (t == null) {
                return null;
            }
            return Long.parseLong(t);
        }
    }
}
/*
解題思路：
1. 建立 Dummy 節點與尾指標 tail，同時遍歷兩串：每步接上較小者並前進。
2. 任一串用盡後，將剩餘整段掛到 tail 之後。
3. 返回 dummy.next 即為合併後串列。輸出時以空白分隔。
時間複雜度 O(n+m)，空間 O(1)（不含輸出）。
 */
