// 題目：Reverse Nodes in k-Group
// 給定鏈結串列 head 與整數 k，將串列中每 k 個節點為一組進行反轉，若最後不足 k 個則保持原樣。
// 只能調整指標，不得改變節點內的值（nodes themselves may be changed）。

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

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }

        // 虛擬頭節點：統一處理「反轉包含頭節點」的情況
        ListNode dummy = new ListNode(0, head);
        // groupPrev 指向每一組的前一個節點
        ListNode groupPrev = dummy;

        while (true) {
            // 找到本組的第 k 個節點（作為本組反轉後的新頭）
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) {
                break;          // 不足 k 個，結束

            }
            ListNode groupNext = kth.next;   // 下一組的起點（本組尾的下一個）

            // 反轉 [groupPrev.next, kth] 這一段；標準原地反轉模板
            ListNode prev = groupNext;       // 反轉時尾端要接回 groupNext
            ListNode curr = groupPrev.next;  // 本組原始頭（反轉後會變成尾）
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // 重新接回到主鏈：groupPrev -> kth(新頭) ... -> 原頭(新尾) -> groupNext
            ListNode newTail = groupPrev.next; // 反轉前的頭，現在是尾
            groupPrev.next = kth;
            groupPrev = newTail;              // 下一輪從本組新尾開始
        }
        return dummy.next;
    }

    // 從 node 開始往後走 k 步，回傳走到的節點；若不足 k 步則回傳 null
    private ListNode getKth(ListNode node, int k) {
        while (node != null && k > 0) {
            node = node.next;
            k--;
        }
        return node;
    }
}

/*
解題思路：
1. 以 dummy 節點接在 head 前，令 groupPrev 指向每組反轉片段的前一個節點。
2. 藉由 getKth(groupPrev, k) 檢查是否有滿 k 個節點可反轉；若不足則結束。
3. 反轉區間 [groupPrev.next, kth]：
   - 使用原地反轉模板，讓 prev 初值為 groupNext（方便收尾拼接）。
   - 迴圈結束後，kth 成為本組新頭，groupPrev.next（原頭）成為新尾。
4. 重新接回主鏈：groupPrev.next 指向 kth，並將 groupPrev 移動到本組新尾，進入下一組。
5. 複雜度：時間 O(n)（每節點恰進出一次），空間 O(1)。
 */
