// 題目：Next Permutation
// 給定整數陣列 nums，將其就地重排為「字典序的下一個排列」。若不存在更大的排列，則就地重排為最小（遞增）序。
// 要求：只能使用常數額外空間（in-place）。

class Solution {

    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = n - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1, n - 1);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[j];
        nums[j] = nums[i];
        nums[i] = t;
    }

    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            int t = nums[l];
            nums[l] = nums[r];
            nums[r] = t;
            l++;
            r--;
        }
    }
}

/*
解題思路：
1. 從右往左找到第一個「下降位」i（nums[i] < nums[i+1]）。若找不到，代表整段為遞減，直接整段反轉為最小序。
2. 再從右往左找到第一個大於 nums[i] 的元素 j，交換 i 與 j。
3. 將區間 [i+1…n-1] 反轉為遞增，使其成為「就近的最小尾巴」。
4. 複雜度：時間 O(n)，空間 O(1)。
 */
