// 題目：Integer to Roman
// 將給定的整數 num（1 <= num <= 3999）轉為等價的羅馬數字字串。
// 羅馬數字與數值對照：I=1, V=5, X=10, L=50, C=100, D=500, M=1000
// 採用「貪婪法」：由大到小嘗試可使用的符號（含減法表示：CM, CD, XC, XL, IX, IV），
/* 只要目前 num 仍大於等於該值，就加入對應符號並扣掉該值，直到 num 歸零為止。 */

class Solution {

    public String intToRoman(int num) {
        // 由大到小列出所有可能的面額，包含減法表示法
        int[] val = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] sym = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();

        // 逐一嘗試面額，能用就一直用（貪婪）
        for (int i = 0; i < val.length; i++) {
            // 只要 num 還夠大，就把對應羅馬符號加入結果並扣值
            while (num >= val[i]) {
                num -= val[i];
                sb.append(sym[i]);
            }
            // 及早結束：若已扣到 0，無需再檢查後續小面額
            if (num == 0) {
                break;
            }
        }
        return sb.toString();
    }
}
/*
解題思路：

1.規則整理：允許的減法組合只有 4/9、40/90、400/900（IV、IX、XL、XC、CD、CM）。

2.將所有面額（含減法）按由大到小排列；掃描時能用就用到不能用為止（貪婪正確性來自面額設計的單調性）。

3.時間複雜度 O(k)：k 為輸出長度（最壞約 15 個字元以内）；空間複雜度 O(1)。
 */
