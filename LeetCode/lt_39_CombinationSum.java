// 題目：Combination Sum
// 給定互異的正整數陣列 candidates 與目標值 target，找出所有總和等於 target 的組合。
// 同一數字可被重複選取多次；兩個組合若某個數字的使用次數不同即視為不同組合。
// 回傳所有唯一組合，順序不限。

import java.util.*;

class Solution {

    private final List<List<Integer>> ans = new ArrayList<>();
    private final List<Integer> path = new ArrayList<>();
    private int[] candidates;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);          // 先排序，便於剪枝
        this.candidates = candidates;
        dfs(0, target);
        return ans;
    }

    // i：目前考慮到的索引；s：剩餘和
    private void dfs(int i, int s) {
        if (s == 0) {                     // 命中目標
            ans.add(new ArrayList<>(path));
            return;
        }
        if (i >= candidates.length || s < candidates[i]) { // 剩餘和小於最小可用值 → 無解
            return;
        }

        // 不選當前數字
        dfs(i + 1, s);

        // 選當前數字（可重複選，所以索引不前進）
        path.add(candidates[i]);
        dfs(i, s - candidates[i]);
        path.remove(path.size() - 1);     // 回溯
    }
}

/*
解題思路：
1. 先對 candidates 升序排序，方便做「剩餘和 < 當前最小候選值」的剪枝。
2. 回溯（DFS）：
   - 狀態為 (i, s)：考慮索引 i 之後的數字，目標剩餘和為 s。
   - 對每個位置有兩種選擇：
     a) 不選 candidates[i] → 轉到 (i+1, s)；
     b) 選 candidates[i] → 轉到 (i, s - candidates[i])（可重複使用，故索引不變）。
3. 當 s==0 時輸出一組答案；若 i 越界或 s 小於當前最小可用值，則無解返回。
4. 唯一性：索引單調不回頭，生成的序列天然非遞減，避免了排列重複。
5. 複雜度：最壞情況為指數級；排序 O(n log n)，回溯受目標與剪枝影響，測資保證組合數 < 150。
 */
