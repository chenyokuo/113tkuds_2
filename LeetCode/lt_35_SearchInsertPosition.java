// 題目：Search Insert Position
// 給定嚴格遞增且元素互異的整數陣列 nums 與整數 target，若找到 target 回傳其索引；
// 若找不到，回傳將 target 插入後仍保持有序時應在的索引位置（以 O(log n) 時間完成）。

class Solution {

    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length; // 以半開區間 [left, right) 進行二分
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] >= target) {
                right = mid;      // 收縮到左半：答案在 [left, mid)
            } else {
                left = mid + 1;   // 收縮到右半：答案在 (mid, right)
            }
        }
        return left; // 此時 left == right，即第一個 >= target 的位置
    }
}

/*
解題思路：
1. 這題本質為 lower_bound（第一個 >= target 的索引）。若陣列中存在 target，該索引即答案；
   否則該索引即為插入位置以維持有序。
2. 使用半開區間 [left, right) 的二分不變量：答案始終落在此區間內。
   - 若 nums[mid] >= target，則答案不會在 mid 右邊，令 right = mid。
   - 否則令 left = mid + 1。
3. 迴圈結束時 left == right，即為所求位置。
4. 複雜度：時間 O(log n)，空間 O(1)。
 */
