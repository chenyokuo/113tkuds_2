// 題目：Swap Nodes in Pairs
// 給定一個單向鏈結串列 head，請將相鄰的每兩個節點交換位置，並回傳交換後的頭節點。
// 限制：不得修改節點中的數值，只能調整指標連結（nodes themselves may be changed）。

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
class Solution {

    public ListNode swapPairs(ListNode head) {
        // 使用虛擬頭節點，處理頭兩個節點也需要交換的情況
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy; // 指向待交換對子的前一個節點

        // 只要還有一對（prev.next 與 prev.next.next）就交換
        while (prev.next != null && prev.next.next != null) {
            // 取出要交換的兩個節點 a、b
            ListNode a = prev.next;
            ListNode b = a.next;

            // 進行交換：prev -> a -> b -> (next)
            // 調整為：   prev -> b -> a -> (next)
            a.next = b.next;  // a 接到 b 的下一個
            b.next = a;       // b 指向 a
            prev.next = b;    // prev 指向新的前方（b）

            // prev 前進到下一對的前一個位置（即交換後的 a）
            prev = a;
        }

        return dummy.next;
    }
}

/*
解題思路：
1. 建立 dummy 節點接在 head 前面，簡化「交換包含頭節點」的情形。
2. 以 prev 指向每一對要交換的前一個節點，兩個待交換節點為 a=prev.next、b=a.next。
3. 指標重排：
   - a.next 指向 b.next
   - b.next 指向 a
   - prev.next 指向 b
   完成一對交換後，prev 前進到 a（因為 a 已被放到後面）。
4. 重複直到沒有完整的一對可交換為止。
5. 複雜度：時間 O(n)，空間 O(1)。
 */
