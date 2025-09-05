// 題目：護理紀錄刪除倒數第 N 筆
// 給定單向鏈結串列（長度 n），刪除倒數第 k 個節點，並輸出刪除後序列（同順序）。

import java.io.*;
import java.util.*;

public class LC19_RemoveNth_Node_Clinic {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);

        // 建立鏈結串列
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int i = 0; i < n; i++) {
            tail.next = new ListNode(in.nextInt());
            tail = tail.next;
        }

        Integer kObj = in.nextInt();
        int k = (kObj == null ? 0 : kObj);

        ListNode head = removeNthFromEnd(dummy.next, k);

        // 輸出刪除後序列（空串則不輸出任何東西）
        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(p.val);
            if (p.next != null) {
                sb.append(' ');
            }
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
    }

    // 雙指標一次遍歷：fast 先走 k 步，之後 fast/slow 同步前進
    private static ListNode removeNthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy, slow = dummy;

        // fast 先走 k+1 步，讓 slow 停在待刪前一節點
        for (int i = 0; i < k + 1; i++) {
            // 題目保證 1 <= k <= n，這裡不需額外檢查越界
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 刪除 slow 之後的節點
        slow.next = slow.next.next;

        return dummy.next;
    }

    // 基本節點定義
    private static class ListNode {

        int val;
        ListNode next;

        ListNode(int v) {
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
    }
}
/*
解題思路：
1. 設置 dummy->head，快指標 fast 先走 k+1 步，使慢指標 slow 與 fast 同步推進時，
   當 fast 到尾（null），slow 正好位於「待刪節點的前一個」。
2. 執行刪除：slow.next = slow.next.next。
3. 返回 dummy.next 為新串起點；印出整串值（以空白分隔）。
時間 O(n)，空間 O(1)。
 */
