// 題目：Remove Nth Node From End of List
// 給定一個鏈結串列 head，刪除倒數第 n 個節點，並回傳刪除後的鏈結串列頭節點。

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

    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 使用虛擬節點(dummy)，避免刪除頭節點時的特殊情況
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        // 先讓 fast 前進 n+1 步，使 fast 與 slow 相差 n 個節點
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // fast 與 slow 一起前進，直到 fast 到達尾端
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 此時 slow 在待刪節點的前一個位置
        slow.next = slow.next.next;

        // 回傳新鏈結串列的頭節點
        return dummy.next;
    }
}

/*
解題思路：
1. 為了方便處理刪除頭節點的情況，建立一個 dummy 節點指向 head。
2. 使用 fast 與 slow 兩個指針：
   - 先讓 fast 前進 n+1 步，確保 fast 與 slow 相差 n 個節點。
   - 然後 fast 與 slow 一起移動，直到 fast 到達鏈尾。
3. 此時 slow 正好位於待刪節點的前一個節點，直接調整 slow.next 指向即可刪除。
4. 回傳 dummy.next 作s為新的頭節點。
5. 時間複雜度 O(sz)，空間複雜度 O(1)。
 */
