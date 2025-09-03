// 題目：Merge Two Sorted Lists
// 給定兩個已排序（非遞減）的鏈結串列 list1 與 list2，將它們合併為一個新的排序鏈結串列，
// 並回傳合併後鏈結串列的頭節點。

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

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 建立虛擬頭節點(dummy)，方便處理邊界情況
        ListNode dummy = new ListNode(-1);
        ListNode curr = dummy;

        // 逐一比較兩串列當前節點，將較小者接到新串列
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        // 若其中一個串列未處理完，直接接上
        if (list1 != null) {
            curr.next = list1;
        }
        if (list2 != null) {
            curr.next = list2;
        }

        // 回傳真正的頭節點
        return dummy.next;
    }
}

/*
解題思路：
1. 使用一個虛擬頭節點 dummy 簡化合併操作，curr 指針負責建構新串列。
2. 同時遍歷 list1 和 list2，比較節點大小，把較小的接到新串列尾端。
3. 最後將剩餘的鏈結串列直接接上（因為它本身已排序）。
4. 回傳 dummy.next 即為合併後的頭節點。
5. 時間複雜度 O(m+n)，m 與 n 分別是 list1 與 list2 的長度。
6. 空間複雜度 O(1)，僅用到常數額外指標。
 */
