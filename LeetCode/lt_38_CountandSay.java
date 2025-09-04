// 題目：Count and Say
// 定義 countAndSay(1) = "1"，對於 n>1，countAndSay(n) 為 countAndSay(n-1) 的「游程編碼」（Run-Length Encoding）。
// 也就是把連續相同字元以「個數 + 字元」的形式串接起來，回傳第 n 個字串。

class Solution {

    public String countAndSay(int n) {
        String s = "1";
        while (--n > 0) {
            StringBuilder t = new StringBuilder();
            for (int i = 0; i < s.length();) {
                int j = i;
                while (j < s.length() && s.charAt(j) == s.charAt(i)) {
                    j++;
                }
                t.append(j - i);          // 次數
                t.append(s.charAt(i));    // 字元
                i = j;                    // 跳到下一段游程
            }
            s = t.toString();
        }
        return s;
    }
}

/*
解題思路：
1. 迭代生成：從字串 "1" 開始，重複 n-1 次對前一個結果做游程編碼（RLE）。
2. 對當前字串 s，使用雙指標 i、j 找出連續相同字元的區間 [i, j)，
   把長度 (j-i) 與字元 s[i] 依序加入到新字串。
3. 更新 s 為新字串，直到迭代完成回傳。
4. 複雜度：設第 n 項長度為 L_n，整體時間約為 O(L_1 + ... + L_n)，
   空間為 O(L_n) 以儲存最後結果。
 */
