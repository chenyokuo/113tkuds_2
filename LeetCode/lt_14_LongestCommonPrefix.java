// 題目：Longest Common Prefix
// 給定字串陣列 strs，找出其中最長的共同字首（prefix）。
// 若沒有共同字首，回傳空字串 ""。

class Solution {

    public String longestCommonPrefix(String[] strs) {
        // 邊界檢查：若陣列為空或 null，直接回傳 ""
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 假設第一個字串為初始前綴
        String prefix = strs[0];

        // 從第二個字串開始逐一比較
        for (int i = 1; i < strs.length; i++) {
            // 當前字串不以 prefix 開頭，縮短 prefix
            while (!strs[i].startsWith(prefix)) {
                // 若 prefix 已縮短為空，則沒有共同前綴
                if (prefix.isEmpty()) {
                    return "";
                }
                // 每次縮短一個字元
                prefix = prefix.substring(0, prefix.length() - 1);
            }
        }
        // 回傳最後得到的共同前綴
        return prefix;
    }
}
/*
解題思路：

1.先假設第一個字串為共同前綴。

2.從第二個字串開始逐一比較，若當前字串不以 prefix 開頭，就縮短 prefix。

3.最後剩下的 prefix 即為最長共同字首；若縮短至空字串，代表不存在共同前綴。

4.時間複雜度：O(S)，其中 S 為所有字元總數；空間複雜度 O(1)。
 */
