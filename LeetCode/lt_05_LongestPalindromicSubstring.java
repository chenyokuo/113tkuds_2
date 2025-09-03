
class Solution {

    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) {
            return s;
        }

        int start = 0, end = 0; // 回文區間 [start, end]
        for (int i = 0; i < n; i++) {
            int len1 = expand(s, i, i);     // 奇數中心
            int len2 = expand(s, i, i + 1); // 偶數中心
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    // 從中心 [L, R] 向外擴展，回傳最終回文長度
    private int expand(String s, int L, int R) {
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1; // 擴過頭一格，長度需 -1 -1
    }
}
