// 題目：3Sum Closest
// 給定整數陣列 nums 與整數 target，找出三個數的總和，使其最接近 target，回傳該總和。
// 保證答案唯一。

class Solution {

    public int threeSumClosest(int[] nums, int target) {
        // 先排序以便使用雙指針
        Arrays.sort(nums);

        int n = nums.length;

        // 以前三個數作為初始最佳解（保證 n >= 3）
        int best = nums[0] + nums[1] + nums[2];

        // 固定一個索引 i，對右側區間做雙指針逼近
        for (int i = 0; i < n - 2; i++) {
            //（可選）略過與前一個相同起點，僅加速，不影響正確性
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];

                // 若更接近 target，更新最佳解
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }

                // 依與 target 的大小關係移動指針
                if (sum < target) {
                    // 總和偏小，嘗試增大 -> 左指針右移
                    l++;
                    //（可選）跳過相同值以小幅加速
                    while (l < r && nums[l] == nums[l - 1]) {
                        l++;
                    }
                } else if (sum > target) {
                    // 總和偏大，嘗試減小 -> 右指針左移
                    r--;
                    while (l < r && nums[r] == nums[r + 1]) {
                        r--;
                    }
                } else {
                    // 完全等於 target，已是最接近
                    return target;
                }
            }
        }
        return best;
    }
}
/*
解題思路：

1.排序後固定 i，對區間 (i+1…n-1) 用雙指針逼近 target。

2.每次計算 sum 與 target 的距離，若更小則更新 best。

3.依 sum 與 target 的比較移動左右指針；若命中等於 target 直接回傳。

4.時間複雜度 O(n^2)，空間複雜度 O(1)（不計排序就地開銷）。
 */
