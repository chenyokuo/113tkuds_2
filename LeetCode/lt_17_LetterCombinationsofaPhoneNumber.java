
import java.util.*;

// 題目：Letter Combinations of a Phone Number
// 給定僅含 '2'~'9' 的字串 digits，回傳它能表示的所有字母組合（順序不限）。
// 若 digits 為空字串，回傳空列表。
class Solution {

    // 電話鍵盤對應表（以索引 2..9 對應）
    private static final String[] MAP = {
        "", "", // 0,1 不使用
        "abc", "def", // 2,3
        "ghi", "jkl", // 4,5
        "mno", "pqrs",// 6,7
        "tuv", "wxyz" // 8,9
    };

    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return ans; // 邊界：空輸入
        }
        // 使用 StringBuilder 動態組字，減少字串建立成本
        backtrack(digits, 0, new StringBuilder(), ans);
        return ans;
    }

    // 回溯：idx 為目前處理到 digits 的第幾位
    private void backtrack(String digits, int idx, StringBuilder path, List<String> ans) {
        // 若已處理完所有位數，收斂為一個完整組合
        if (idx == digits.length()) {
            ans.add(path.toString());
            return;
        }

        // 取得本位數字對應的字母集合
        int key = digits.charAt(idx) - '0';
        String letters = MAP[key];

        // 逐一嘗試每個字母並遞迴到下一位
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            backtrack(digits, idx + 1, path, ans);
            path.deleteCharAt(path.length() - 1); // 回退（撤銷選擇）
        }
    }
}
/*
解題思路：

1.以鍵盤映射表將每一位數字轉成字母集合。

2.用回溯法從第 0 位開始，對每一位枚舉所有可能字母，當長度等於 digits 長度時加入答案。

3.邊界：digits 為空直接回傳空列表。

4.時間複雜度 O(3^n · n)（其中部分位數為 4 個字母），空間複雜度 O(n) 為遞迴深度。
 */
