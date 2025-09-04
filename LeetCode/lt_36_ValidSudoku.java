// 題目：Valid Sudoku
// 給定 9x9 的數獨盤面（含數字 '1'~'9' 與 '.' 空白），判斷目前盤面是否有效：
// 1) 每列不重複、2) 每行不重複、3) 每個 3x3 子盒不重複（僅需檢查已填入的格子）。

class Solution {

    public boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9]; // row[i][d]：第 i 列是否出現數字 d
        boolean[][] col = new boolean[9][9]; // col[j][d]：第 j 行是否出現數字 d
        boolean[][] box = new boolean[9][9]; // box[k][d]：第 k 個 3x3 盒是否出現數字 d（k= (i/3)*3 + j/3）

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }

                int d = c - '1';                 // 將 '1'~'9' 映成 0..8
                int k = (i / 3) * 3 + (j / 3);   // 對應的 3x3 盒索引

                if (row[i][d] || col[j][d] || box[k][d]) {
                    return false;
                }

                row[i][d] = col[j][d] = box[k][d] = true;
            }
        }
        return true;
    }
}

/*
解題思路：
1. 以三個 9x9 布林表分別記錄「列 / 行 / 3x3 盒」是否已出現某數字。
2. 對每個已填入的格子 (i,j)：
   - 將字元轉成索引 d = s[i][j]-'1'（0..8）；
   - 計算盒索引 k = (i/3)*3 + j/3；
   - 若 row[i][d]、col[j][d] 或 box[k][d] 任何一者已為 true，代表重複，回傳 false；
   - 否則標記為已出現。
3. 全部檢查完無衝突即為有效。
4. 複雜度：時間 O(81) ≈ O(1)，空間 O(81) ≈ O(1)。
 */
