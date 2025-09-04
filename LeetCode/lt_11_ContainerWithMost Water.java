// 題目：Container With Most Water
// 給定陣列 height，其中 height[i] 代表第 i 根垂直線的高度（相鄰線間距為 1）。
// 請找出兩根線與 x 軸形成的容器所能盛水的最大面積。

class Solution {

    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int ans = 0;
        while (i < j) {
            int t = Math.min(height[i], height[j]) * (j - i);
            ans = Math.max(ans, t);
            if (height[i] < height[j]) {
                ++i;
            } else {
                --j;
            }
        }
        return ans;
    }
}
/*
解題思路：

1.面積由「短板」決定：若固定較矮的一側，無論另一側怎麼移動，高度都不會增加，因此必須移動較矮者尋找更高的可能。

2.以雙指針從最外側開始，計算每一步面積並更新答案；再依高度比較決定移動哪一邊。

3.時間複雜度 O(n)：左右指針各自最多走過陣列一次。

4.空間複雜度 O(1)：只使用常數額外空間。
 */
