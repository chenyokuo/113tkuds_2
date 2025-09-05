// 題目：手機門號組合
// 給定數字字串（僅含 '2'–'9'），依標準電話鍵盤展開所有可能字母組合，每行輸出一組。

import java.io.*;

public class LC17_PhoneCombos_CSShift {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String digits = br.readLine();
        if (digits == null) {
            digits = "";
        }
        digits = digits.trim();

        if (digits.isEmpty()) {
            return; // 空字串 → 無輸出
        }
        String[] map = {
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs", // 7
            "tuv", // 8
            "wxyz" // 9
        };

        StringBuilder path = new StringBuilder();
        dfs(digits, 0, map, path);
    }

    // 回溯逐位展開：一層處理一個 digit
    private static void dfs(String digits, int idx, String[] map, StringBuilder path) {
        if (idx == digits.length()) {
            System.out.println(path.toString());
            return;
        }
        int mi = digits.charAt(idx) - '2'; // 映射到 0..7
        String letters = map[mi];
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            dfs(digits, idx + 1, map, path);
            path.setLength(path.length() - 1); // 回溯
        }
    }
}
/*
解題思路：
1. 建立電話鍵盤映射（'2'→"abc", ... '9'→"wxyz"），索引為 digit-'2'。
2. 以回溯法：每層處理一個數字，對其對應的所有字母展開分支，使用 StringBuilder 暫存路徑。
3. 抵達末層即輸出當前路徑。時間複雜度為乘積（最多 4^L），空間 O(L)（遞迴深度）。
 */
