// 題目：Longest Valid Parentheses
// 給定只含 '(' 與 ')' 的字串 s，回傳最長的「有效括號」子字串長度。

class Solution {

    public int longestValidParentheses(String s) {
        int n = s.length();
        int[] f = new int[n + 1];
        int ans = 0;
        for (int i = 2; i <= n; ++i) {
            if (s.charAt(i - 1) == ')') {
                if (s.charAt(i - 2) == '(') {
                    f[i] = f[i - 2] + 2;
                } else {
                    int j = i - f[i - 1] - 1;
                    if (j > 0 && s.charAt(j - 1) == '(') {
                        f[i] = f[i - 1] + 2 + f[j - 1];
                    }
                }
                ans = Math.max(ans, f[i]);
            }
        }
        return ans;
    }
}
/*
解題思路（DP 版）：
1. 定義 f[i]：以 s[i-1] 作為結尾的最長有效括號長度（i 從 1 到 n）。
2. 轉移：
   - 若 s[i-1] == ')' 且 s[i-2] == '('，則 f[i] = f[i-2] + 2。
   - 若 s[i-1] == ')' 且 s[i-2] == ')'，令 j = i - f[i-1] - 1，若 j>0 且 s[j-1]=='('，
     則 f[i] = f[i-1] + 2 + f[j-1]。
3. 每步更新答案為 max(ans, f[i])。
4. 時間複雜度 O(n)，空間複雜度 O(n)。
 */
