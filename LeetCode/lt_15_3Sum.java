
import java.util.*;

// 題目：3Sum
// 給定整數陣列 nums，找出所有「不重複」且 a + b + c = 0 的三元組。
// 請回傳所有三元組（順序不影響），不得包含重複組合。
class Solution {

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return ans; // 邊界：不足三個元素
        }
        Arrays.sort(nums);          // 先排序，便於雙指針與去重
        int n = nums.length;

        // 以 i 為固定點，從 i 右側用雙指針找兩數和 = -nums[i]
        for (int i = 0; i < n - 2; i++) {
            if (nums[i] > 0) {
                break;             // 若當前為正，後面也為正，不可能湊 0

            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去重：跳過相同的起點
            }
            int l = i + 1, r = n - 1;
            int target = -nums[i];

            while (l < r) {
                int sum = nums[l] + nums[r];

                if (sum == target) {
                    // 命中一組解，加入答案
                    ans.add(Arrays.asList(nums[i], nums[l], nums[r]));

                    // 去重：跳過與當前 l、r 相同的值，避免重複三元組
                    int lv = nums[l], rv = nums[r];
                    while (l < r && nums[l] == lv) {
                        l++;
                    }
                    while (l < r && nums[r] == rv) {
                        r--;
                    }
                } else if (sum < target) {
                    l++; // 和太小，左指針右移以增加總和
                } else {
                    r--; // 和太大，右指針左移以減少總和
                }
            }
        }
        return ans;
    }
}
/*
解題思路：

1.排序後固定一個索引 i，於右邊用雙指針 l、r 搜尋兩數和 = -nums[i]。

2.去重策略：

    外層 i：若 nums[i] 與前一個相同直接略過。

    內層命中後：l、r 分別跳過與當前值相同的連續區段。

3.及早剪枝：當 nums[i] > 0 時可直接結束外層迴圈。

4.時間複雜度：O(n^2)（排序 O(n log n) + 兩層掃描）；空間複雜度：O(1) 额外空間（不計輸出）。
 */
