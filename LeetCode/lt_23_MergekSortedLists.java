// 題目：Merge k Sorted Lists
// 給定一個由 k 條已排序（升序）的鏈結串列所組成的陣列 lists，
// 將所有鏈結串列合併為一條排序後的鏈結串列並回傳其頭節點。

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
import java.util.*;

class Solution {

    public ListNode mergeKLists(ListNode[] lists) {
        // 邊界：空陣列或全部為空
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 最小堆：依節點值排序，維持「目前各串列的最小未取節點」
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));

        // 將每條鏈結串列的頭節點丟進堆（若非空）
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }

        // dummy/tail 用於建構輸出串列
        ListNode dummy = new ListNode(-1);
        ListNode tail = dummy;

        // 反覆彈出最小節點，接到結果尾端；若該節點仍有 next，將 next 放回堆
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            tail.next = node;
            tail = tail.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }

        return dummy.next;
    }
}

/*
解題思路：
1. 使用最小堆（PriorityQueue）維持 k 條鏈結串列當前頭節點中的最小者。
2. 初始將每條串列的第一個節點（若存在）放入堆。
3. 迭代：
   - 取出堆頂（目前最小值）接到答案尾端；
   - 若該節點有下一個節點，將其放回堆中。
4. 如此可在合併過程中保持整體升序。

複雜度：
- 設所有節點總數為 N、串列數為 k。
- 時間：O(N log k)（每個節點最多進出堆一次）。
- 空間：O(k) 堆的大小（不計輸出串列）。
 */
