// 題目：Combination Sum II
// 給定整數陣列 candidates 與目標值 target，找出所有總和等於 target 的「唯一組合」。
// 限制：每個數字最多只能用一次；答案不得包含重複組合（順序不限）。

class Solution {

    private java.util.List<java.util.List<Integer>> ans = new java.util.ArrayList<>();
    private java.util.List<Integer> t = new java.util.ArrayList<>();
    private int[] candidates;

    public java.util.List<java.util.List<Integer>> combinationSum2(int[] candidates, int target) {
        java.util.Arrays.sort(candidates); // 排序以便剪枝與去重
        this.candidates = candidates;
        dfs(0, target);
        return ans;
    }

    private void dfs(int i, int s) {
        if (s == 0) {                          // 命中一組
            ans.add(new java.util.ArrayList<>(t));
            return;
        }
        if (i >= candidates.length || s < candidates[i]) { // 剪枝：剩餘和小於最小可用值
            return;
        }
        for (int j = i; j < candidates.length; ++j) {
            if (j > i && candidates[j] == candidates[j - 1]) {
                continue; // 同層去重

            }
            t.add(candidates[j]);                 // 選用一次 candidates[j]
            dfs(j + 1, s - candidates[j]);       // 下一步從 j+1 開始（每數最多一次）
            t.remove(t.size() - 1);               // 回溯
        }
    }
}

/*
解題思路：
1. 先排序，使相同數字相鄰，便於剪枝與去重。
2. 回溯（DFS）：
   - 狀態 (i, s)：從索引 i 開始，目標剩餘和為 s。
   - 迴圈 j 從 i 到末尾，對每個候選值嘗試一次，遞迴到 (j+1, s - candidates[j])。
   - 當 s==0 時收錄當前組合；若 i 越界或 s < candidates[i]（最小值）則無解返回。
3. 去重關鍵（同層去重）：
   - 若 j>i 且 candidates[j] == candidates[j-1]，跳過；避免在同一層以相同數值作為起點造成重複組合。
4. 每個元素只用一次：遞迴時索引改為 j+1。
5. 複雜度：排序 O(n log n)；回溯在最壞情況為指數級（答案數量 < 150，實測可接受）。
   空間 O(n) 為路徑與遞迴深度。
 */
