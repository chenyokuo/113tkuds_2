// 題目：Remove Element
// 給定整數陣列 nums 與整數 val，請「原地」移除所有等於 val 的元素，並回傳不等於 val 的元素個數 k。
// 要求：將前 k 個位置填入所有不等於 val 的元素（順序可改變，但此寫法保留原順序），其餘位置內容不重要。

class Solution {

    public int removeElement(int[] nums, int val) {
        int k = 0; // 寫入指標：下一個「保留元素」要寫入的位置
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {    // 只保留不等於 val 的元素
                nums[k] = nums[i];   // 覆寫到前段
                k++;                 // 擴張有效區間 [0..k-1]
            }
        }
        return k; // 回傳保留的元素個數
    }
}

/*
解題思路：
1. 兩指針（快慢指針）：
   - i：遍歷整個陣列；
   - k：寫入不等於 val 的元素，使前段 [0..k-1] 永遠是保留區。
2. 當 nums[i] != val 時，將其寫到 nums[k] 並遞增 k；等於 val 則跳過。
3. 迴圈結束後，前 k 個即為需要保留的元素；其後內容不影響答案。
4. 複雜度：時間 O(n)，空間 O(1)。

補充（可選的非穩定寫法，寫入更少）：
- 以雙端指針 i 從左、n 從右：
  while (i < n) { if (nums[i] == val) nums[i] = nums[--n]; else i++; }
  最終回傳 n；此法不保證相對順序，但可能減少覆寫次數。
 */
