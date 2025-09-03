// 題目：Container With Most Water
// 給定陣列 height，其中 height[i] 代表第 i 根垂直線的高度（相鄰線間距為 1）。
// 請找出兩根線與 x 軸形成的容器所能盛水的最大面積。

class Solution {

    public int maxArea(int[] height) {
        // 以左右雙指針從邊界往內逼近
        int l = 0, r = height.length - 1;

        // best 用來記錄目前可得到的最大面積
        int best = 0;

        // 當左右指針尚未相遇時持續嘗試
        while (l < r) {
            // 由兩側高度的較小者決定可盛水的高度
            int h = Math.min(height[l], height[r]);

            // 面積 = 高度 * 寬度（寬度為 r - l）
            best = Math.max(best, h * (r - l));

            // 關鍵策略：移動「較矮」的那一側，才有機會提高高度，進而獲得更大面積
            if (height[l] < height[r]) {
                l++;      // 左側較矮 → 嘗試往內找更高的左邊
            } else {
                r--;      // 右側較矮或相等 → 往內找更高的右邊
            }
        }

        // 回傳最大面積
        return best;
    }
}
/*
解題思路：

1.面積由「短板」決定：若固定較矮的一側，無論另一側怎麼移動，高度都不會增加，因此必須移動較矮者尋找更高的可能。

2.以雙指針從最外側開始，計算每一步面積並更新答案；再依高度比較決定移動哪一邊。

3.時間複雜度 O(n)：左右指針各自最多走過陣列一次。

4.空間複雜度 O(1)：只使用常數額外空間。
 */
