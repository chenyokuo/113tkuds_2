// 題目：Search in Rotated Sorted Array
// 給定可能經旋轉的嚴格遞增陣列 nums 與整數 target，請在 O(log n) 時間內回傳 target 的索引；若不存在回傳 -1。

class Solution {

    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] == target) {
                return m;
            }

            // 判斷哪一半是有序的
            if (nums[l] <= nums[m]) {                 // 左半有序
                if (nums[l] <= target && target < nums[m]) {
                    r = m - 1;                        // 目標在左半
                } else {
                    l = m + 1;                        // 丟棄左半
                }
            } else {                                   // 右半有序
                if (nums[m] < target && target <= nums[r]) {
                    l = m + 1;                        // 目標在右半
                } else {
                    r = m - 1;                        // 丟棄右半
                }
            }
        }
        return -1;
    }
}

/*
解題思路：
1. 旋轉後的陣列在任一時刻總有一側區間保持有序（左或右）。
2. 二分時先判斷左半是否有序（nums[l] <= nums[m]）：
   - 若左半有序，檢查 target 是否落在 [nums[l], nums[m])，是則縮右界；否則丟棄左半。
   - 若右半有序，檢查 target 是否落在 (nums[m], nums[r]]，是則縮左界；否則丟棄右半。
3. 命中即回傳索引；未命中則最終回傳 -1。
4. 複雜度：時間 O(log n)，空間 O(1)。
 */
