// 題目：Generate Parentheses
// 給定 n 對括號，請產生所有可能且有效的括號組合。
// 有效括號需符合：每個左括號必須由右括號關閉，且順序正確。

import java.util.*;

class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        return ans;
    }

    // 回溯函數
    private void backtrack(List<String> ans, StringBuilder path, int open, int close, int n) {
        // 若字串長度達到 2*n，代表完成一組合法組合
        if (path.length() == 2 * n) {
            ans.add(path.toString());
            return;
        }

        // 可以加入左括號，只要 open 還沒用完
        if (open < n) {
            path.append('(');
            backtrack(ans, path, open + 1, close, n);
            path.deleteCharAt(path.length() - 1); // 回溯
        }

        // 可以加入右括號，只要右括號數量小於左括號
        if (close < open) {
            path.append(')');
            backtrack(ans, path, open, close + 1, n);
            path.deleteCharAt(path.length() - 1); // 回溯
        }
    }
}

/*
解題思路：
1. 採用回溯法（DFS）生成所有可能組合。
2. 狀態變數：
   - open：已使用的左括號數。
   - close：已使用的右括號數。
3. 規則：
   - 若 open < n，可以再加左括號 '('。
   - 若 close < open，可以再加右括號 ')'。
   - 當長度達到 2*n 時，代表生成了一組完整合法組合。
4. 時間複雜度：O(4^n / sqrt(n))，為卡特蘭數的漸進上界。
5. 空間複雜度：O(n)，遞迴深度與暫存字串長度。
 */
