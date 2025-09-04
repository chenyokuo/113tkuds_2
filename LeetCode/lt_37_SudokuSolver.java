// 題目：Sudoku Solver
// 將 9x9 的數獨盤面（以 '.' 表示空格）填滿，使其成為一個合法解：
// 每列、每行、每個 3x3 宮格都恰好包含一次 1~9。請原地修改 board。

import java.util.*;

class Solution {

    private boolean solved;
    private char[][] board;
    private final List<Integer> empties = new ArrayList<>();
    private final boolean[][] row = new boolean[9][9];
    private final boolean[][] col = new boolean[9][9];
    private final boolean[][][] box = new boolean[3][3][9];

    public void solveSudoku(char[][] board) {
        this.board = board;

        // 建立狀態：已填數字的列/行/宮格佔用情況；收集所有空格索引
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    empties.add(i * 9 + j);
                } else {
                    int d = board[i][j] - '1';
                    row[i][d] = col[j][d] = box[i / 3][j / 3][d] = true;
                }
            }
        }

        dfs(0); // 依序填空
    }

    private void dfs(int k) {
        if (k == empties.size()) { // 全部填完
            solved = true;
            return;
        }

        int idx = empties.get(k);
        int i = idx / 9, j = idx % 9;

        for (int d = 0; d < 9; ++d) {
            if (!row[i][d] && !col[j][d] && !box[i / 3][j / 3][d]) {
                // 嘗試放置數字 d+1
                row[i][d] = col[j][d] = box[i / 3][j / 3][d] = true;
                board[i][j] = (char) (d + '1');

                dfs(k + 1);
                if (solved) {
                    return;          // 已找到解，保持當前填法並逐層返回
                }
                // 回溯
                row[i][d] = col[j][d] = box[i / 3][j / 3][d] = false;
                board[i][j] = '.';
            }
        }
    }
}

/*
解題思路：
1. 狀態紀錄：
   - row[i][d] / col[j][d] / box[p][q][d] 代表「第 i 列 / j 行 / (p,q) 宮」是否已用數字 d+1。
   - 將所有空格的位置收集到列表 empties。
2. 回溯（DFS）：
   - 依序對 empties[k] 嘗試填入 1..9，僅當該數在對應列/行/宮皆未使用時才可放。
   - 放置後遞迴下一格；若成功（到達末端），立刻逐層 return，保留當前填法。
   - 若失敗則撤銷標記與盤面字元，回溯嘗試下一數字。
3. 正確性：佔用表確保每一步均滿足三大約束；回溯能遍歷所有合法填法，找到一組解即停止。
4. 複雜度：最壞情況為指數級，但藉由三維佔用表與即時剪枝，實際效能良好；額外空間 O(1)（常數大小表）。
 */
