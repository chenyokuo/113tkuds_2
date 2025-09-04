// 題目：Find First and Last Position of Element in Sorted Array
// 給定非遞減排序的整數陣列 nums 與 target，請以 O(log n) 時間回傳 target 的起始與結束索引；若不存在回傳 [-1, -1]。

class Solution {

    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int l = lowerBound(nums, target);       // 第一個 >= target 的位置
        if (l == n || nums[l] != target) {
            return new int[]{-1, -1};          // 沒找到 target
        }
        int r = upperBound(nums, target) - 1;   // 最後一個 <= target 的位置
        return new int[]{l, r};
    }

    // 回傳第一個使得 a[idx] >= x 的 idx（若皆小於 x，回傳 a.length）
    private int lowerBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (a[m] < x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }

    // 回傳第一個使得 a[idx] > x 的 idx（若皆 <= x，回傳 a.length）
    private int upperBound(int[] a, int x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = l + ((r - l) >> 1);
            if (a[m] <= x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
        return l;
    }
}

/*
解題思路：
1. 使用兩次二分搜尋：
   - lowerBound：找第一個 >= target 的位置 l。
   - upperBound：找第一個 > target 的位置，再減一得到 r。
2. 若 l 超界或 nums[l] != target，代表不存在，回傳 [-1, -1]。
3. 複雜度：時間 O(log n)，空間 O(1)。
 */
