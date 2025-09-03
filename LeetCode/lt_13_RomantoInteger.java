// 題目：Roman to Integer
// 將給定的羅馬數字字串轉換為整數。
// 羅馬數字對應值：I=1, V=5, X=10, L=50, C=100, D=500, M=1000
// 規則：若較小的數字出現在較大的數字前，則採減法（如 IV=4, IX=9, XL=40, XC=90, CD=400, CM=900）。

class Solution {

    public int romanToInt(String s) {
        int n = s.length(), ans = 0;

        // 從左到右掃描字串
        for (int i = 0; i < n; i++) {
            int v = val(s.charAt(i)); // 當前符號對應的數值

            // 若下一個符號存在且比當前符號大，代表是減法情況
            if (i + 1 < n && v < val(s.charAt(i + 1))) {
                ans -= v; // 減去當前值
            } else {
                ans += v; // 否則直接加總
            }
        }

        return ans; // 回傳最終整數值
    }

    // 將羅馬字元轉換為對應整數
    private int val(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            default:
                return 1000; // 'M'
        }
    }
}
/*
解題思路：

1.從左至右讀取字元，先取得當前符號數值 v。

2.若下一個數字存在且比 v 大，代表需減去 v（處理減法組合）。

3.否則將 v 加入答案。

4.時間複雜度 O(n)，空間複雜度 O(1)。
 */
