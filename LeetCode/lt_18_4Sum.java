
import java.util.*;

// 題目：4Sum
// 給定整數陣列 nums 和整數 target，找出所有不重複的四元組 (a, b, c, d)，
// 使得 nums[a] + nums[b] + nums[c] + nums[d] == target。
// 可以任意順序回傳解答。
class Solution {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return ans;
        }

        Arrays.sort(nums); // 排序以便雙指針和去重
        int n = nums.length;

        // 第一層：固定 i
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 跳過重複的 i
            }
            // 第二層：固定 j
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // 跳過重複的 j
                }
                int l = j + 1, r = n - 1;

                // 由於數值範圍大（可到 ±10^9），避免溢位，用 long 計算
                long t = (long) target - nums[i] - nums[j];

                // 內層雙指針
                while (l < r) {
                    long sum = nums[l] + nums[r];
                    if (sum == t) {
                        ans.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        int lv = nums[l], rv = nums[r];
                        // 跳過重複的 l 與 r
                        while (l < r && nums[l] == lv) {
                            l++;
                        }
                        while (l < r && nums[r] == rv) {
                            r--;
                        }
                    } else if (sum < t) {
                        l++; // 總和偏小，左指針右移
                    } else {
                        r--; // 總和偏大，右指針左移
                    }
                }
            }
        }
        return ans;
    }
}
/*
解題思路：
1. 排序：將陣列排序，方便雙指針處理與去重。
2. 雙層固定 + 雙指針：
   - 外層兩層迴圈固定前兩個數 i、j。
   - 內層使用雙指針 l、r 逼近剩下兩數。
3. 去重策略：
   - i 與 j 若與前一個值相同就跳過。
   - 命中答案後，l 與 r 也需要跳過重複值。
4. 溢位處理：由於 nums[i] 與 target 都可能到 ±10^9，四數和可能超過 int，因此用 long 處理中間結果。

時間複雜度：O(n^3)
空間複雜度：O(1)（不計輸出結果）
 */
