// 題目：Generate Parentheses
// 給定 n 對括號，請產生所有可能且有效的括號組合。
// 有效括號需符合：每個左括號必須由右括號關閉，且順序正確。

class Solution {

    private List<String> ans = new ArrayList<>();
    private int n;

    public List<String> generateParenthesis(int n) {
        this.n = n;
        dfs(0, 0, "");
        return ans;
    }

    private void dfs(int l, int r, String t) {
        if (l > n || r > n || l < r) {
            return;
        }
        if (l == n && r == n) {
            ans.add(t);
            return;
        }
        dfs(l + 1, r, t + "(");
        dfs(l, r + 1, t + ")");
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
