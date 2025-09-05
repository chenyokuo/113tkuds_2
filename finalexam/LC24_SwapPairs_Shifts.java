// 題目：班表兩兩交換
// 給定一行整數（以單向鏈結串列表示班表），請將相鄰節點成對交換（1↔2, 3↔4, …），奇數長度最後一個保留。

import java.io.*;
import java.util.*;

public class LC24_SwapPairs_Shifts {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 只讀取一行（題目格式：一行整數）
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        StringTokenizer st = new StringTokenizer(line);
        ListNode head = null, tail = null;
        while (st.hasMoreTokens()) {
            long v = Long.parseLong(st.nextToken());
            ListNode node = new ListNode(v);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        head = swapPairs(head);

        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(p.val);
            if (p.next != null) {
                sb.append(' ');
            }
        }
        System.out.println(sb.toString());
    }

    // 核心：Dummy + prev 指標；交換 (a,b)：prev.next=b; a.next=b.next; b.next=a
    private static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            ListNode a = prev.next;
            ListNode b = a.next;

            a.next = b.next;
            b.next = a;
            prev.next = b;

            prev = a; // 下一對
        }
        return dummy.next;
    }

    private static class ListNode {

        long val;
        ListNode next;

        ListNode(long v) {
            this.val = v;
        }
    }
}
/*
解題思路：
1) 依題意只讀一行，建立鏈結串列。
2) 使用 Dummy+prev，按對交換 (a,b)：prev.next=b; a.next=b.next; b.next=a。
3) 逐對處理到尾；奇數長度最後一個保留。
時間 O(n)，空間 O(1)。
 */
