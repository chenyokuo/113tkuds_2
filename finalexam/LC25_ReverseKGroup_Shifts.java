// 題目：班表 k 組反轉
// 給定單向鏈結串列與整數 k，將鏈結串列以 k 節點一組進行區段反轉；若最後不足 k，則保持原樣。

import java.io.*;
import java.util.*;

public class LC25_ReverseKGroup_Shifts {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀取 k
        String lineK = br.readLine();
        if (lineK == null || lineK.trim().isEmpty()) {
            return;
        }
        int k = Integer.parseInt(lineK.trim());

        // 讀取一行序列（允許為空，表示長度 0）
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) {
            // 空序列 → 不輸出任何東西
            return;
        }

        // 建立鏈結串列
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

        // k 組反轉
        head = reverseKGroup(head, k);

        // 輸出
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

    // 迭代：每次確認是否有滿 k 個節點，若有則原地反轉該區段
    private static ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            // 找到本組的第 k 個節點（groupPrev 之後的第 k 個）
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break; // 不足 k：結束
            }
            ListNode groupNext = kth.next;   // 下一段起點
            ListNode curr = groupPrev.next;  // 本段起點（反轉前）
            ListNode prev = groupNext;

            // 原地反轉 [curr .. kth]，以 groupNext 為邊界
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // 連接：groupPrev -> kth（反轉後的頭）；原頭成為尾巴
            ListNode newTail = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = newTail;
        }
        return dummy.next;
    }

    // 基本節點定義（使用 long；僅重排不改值）
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
1. 使用 dummy 與 groupPrev，每輪檢查是否有滿 k 個節點可反轉。
2. 若足夠：以迭代方式將 [groupPrev.next .. kth] 原地反轉（邊界為 groupNext）。
3. 反轉後接回：groupPrev.next = kth；原段起點成為新尾巴 groupPrev，開始下一輪。
時間 O(n)，空間 O(1)。
 */
