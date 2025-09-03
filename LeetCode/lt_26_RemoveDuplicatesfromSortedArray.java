// 題目：Remove Duplicates from Sorted Array
// 給定已排序（非遞減）的整數陣列 nums，請原地移除重複元素，讓每個唯一元素只出現一次，並回傳唯一元素個數 k。
// 要求：前 k 個位置必須依原本出現順序放入「不重複元素」，只可使用 O(1) 額外空間。

class Solution {

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int k = 1; // 寫入指標：下一個「唯一元素」要寫入的位置，初始放過 nums[0]
        for (int i = 1; i < nums.length; i++) {
            // 當前元素與最後一個寫入的唯一元素不同，即為新唯一值
            if (nums[i] != nums[k - 1]) {
                nums[k] = nums[i]; // 將新唯一值寫到前段
                k++;               // 擴張唯一區間 [0..k-1]
            }
        }
        return k; // 回傳唯一元素個數
    }
}

/*
解題思路：
1. 兩指針（快慢指針）：
   - i：掃描整個陣列的讀指針（從 1 開始）。
   - k：寫入指針，維持區間 nums[0..k-1] 皆為目前收集到的唯一元素。
2. 若 nums[i] 與 nums[k-1] 不同，表示遇到新元素，將其寫入 nums[k] 並遞增 k。
3. 迴圈結束後，前 k 個元素即為不重複且保持相對順序的結果。
4. 複雜度：時間 O(n)，空間 O(1)。
 */
