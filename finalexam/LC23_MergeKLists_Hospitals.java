// 題目：多院區即時叫號合併
// 給定 k 條已升序排序的單向鏈結串列，請以 O(N log k) 將它們合併為一條升序串列並輸出結果。

import java.io.*;
import java.util.*;

public class LC23_MergeKLists_Hospitals {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer kObj = in.nextInt();
        int k = (kObj == null ? 0 : kObj);

        // 讀入 k 行串列（每行以 -1 結尾；可能為空行：直接 -1）
        ListNode[] lists = new ListNode[Math.max(k, 0)];
        for (int i = 0; i < k; i++) {
            ListNode head = null, tail = null;
            while (true) {
                Long vObj = in.nextLong();
                if (vObj == null) {
                    head = head;
                    break;
                } // EOF 防禦
                long v = vObj;
                if (v == -1L) {
                    break;
                }
                ListNode node = new ListNode(v);
                if (head == null) {
                    head = tail = node;
                } else {
                    tail.next = node;
                    tail = node;
                }
            }
            lists[i] = head;
        }

        ListNode merged = mergeKLists(lists);

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

    // 最小堆合併：時間 O(N log k)，空間 O(k)
    private static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingLong(n -> n.val));
        for (ListNode h : lists) {
            if (h != null) {
                pq.offer(h);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            tail.next = cur;
            tail = tail.next;
            if (cur.next != null) {
                pq.offer(cur.next);
            }
        }
        return dummy.next;
    }

    // 節點定義（用 long 以涵蓋 ±1e9 並保守擴充）
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
1. 初始將每條非空串列的頭節點放入最小堆（值小者優先）。
2. 反覆從堆中彈出最小節點，接到結果尾端；若該節點有 next，將 next 入堆。
3. 直到堆空即完成合併。時間 O(N log k)，空間 O(k)。輸入以 -1 作為每行串列結尾。
 */
