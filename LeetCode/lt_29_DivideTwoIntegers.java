// 題目：Divide Two Integers
// 在不使用乘法、除法與取餘數運算的情況下，計算整數 dividend ÷ divisor 的商（向 0 取整）。
// 若結果超過 32 位元整數範圍，需截斷為 Integer.MAX_VALUE 或 Integer.MIN_VALUE。

class Solution {

    public int divide(int dividend, int divisor) {
        // 特判：divisor 不會為 0；唯一可能溢位的是 MIN_VALUE / -1
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 以 long 取絕對值，避免 |MIN_VALUE| 溢位
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        // 透過位移累減的「二分逼近」：從高位到低位嘗試 b * 2^i
        long res = 0;
        for (int i = 31; i >= 0; i--) {
            // 用 (a >> i) >= b 避免 (b << i) 溢位比較
            if ((a >> i) >= b) {
                a -= (b << i);     // 扣掉對應倍數
                res += (1L << i);  // 商累加 2^i
            }
        }

        // 處理正負號
        res = ((dividend ^ divisor) < 0) ? -res : res;

        // 依題意邊界截斷（理論上除了 MIN_VALUE / -1 以外不會超界，但保險起見）
        if (res > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (res < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) res;
    }
}

/*
解題思路：
1. 將除法轉為「減去 divisor 的倍數」：用位移快速找到不超過 dividend 的最大倍數 b*2^i，
   從 i = 31 到 0 嘗試，能扣就扣並把商加上 2^i，直到被除數剩餘 < divisor。
2. 使用 long 存放絕對值，避免 |Integer.MIN_VALUE| 溢位；比較時用 (a >> i) >= b 避免 (b << i) 溢位。
3. 正負號由 dividend 與 divisor 的符號異同決定；最後做範圍截斷處理。
4. 時間複雜度 O(32) ≈ O(1)，空間複雜度 O(1)。
 */
