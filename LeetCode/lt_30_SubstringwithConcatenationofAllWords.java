// 題目：Substring with Concatenation of All Words
// 給定字串 s 與字串陣列 words（每個 word 長度相同），找出 s 中所有起點索引，
// 使得從該起點截取長度為 words.length * wordLen 的子字串，恰為 words 的某種排列串接（每個 word 使用次數等於在 words 中的次數）。

import java.util.*;

class Solution {

    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || words == null || words.length == 0) {
            return ans;
        }

        int n = s.length();
        int m = words.length;
        int w = words[0].length();
        int windowLen = m * w;
        if (n < windowLen) {
            return ans;
        }

        // 目標詞頻表
        Map<String, Integer> need = new HashMap<>();
        for (String wd : words) {
            need.put(wd, need.getOrDefault(wd, 0) + 1);
        }

        // 對齊掃描：起點偏移 0..w-1
        for (int offset = 0; offset < w; offset++) {
            int left = offset;                 // 視窗左端（對齊到 word 邊界）
            int count = 0;                     // 視窗內已匹配的 word 數
            Map<String, Integer> have = new HashMap<>();

            // 以步長 w 往右擴展
            for (int right = offset; right + w <= n; right += w) {
                String word = s.substring(right, right + w);

                if (!need.containsKey(word)) {
                    // 非目標詞：清空視窗，從下一格重來
                    have.clear();
                    count = 0;
                    left = right + w;
                    continue;
                }

                // 納入視窗
                have.put(word, have.getOrDefault(word, 0) + 1);
                count++;

                // 若某詞超標，收縮左端直到不超標
                while (have.get(word) > need.get(word)) {
                    String drop = s.substring(left, left + w);
                    have.put(drop, have.get(drop) - 1);
                    left += w;
                    count--;
                }

                // 若湊滿 m 個詞，記錄答案並滑動一格（彈出最左一個詞，尋找下一個起點）
                if (count == m) {
                    ans.add(left);
                    String drop = s.substring(left, left + w);
                    have.put(drop, have.get(drop) - 1);
                    left += w;
                    count--;
                }
            }
        }
        return ans;
    }
}

/*
解題思路：
1) 觀察：每個 word 長度固定為 w，欲匹配的子字串長度固定為 m*w。用滑動視窗，但只需檢查與 w 對齊的起點。
2) 針對每個偏移 offset ∈ [0..w-1]，以步長 w 往右掃描，維護視窗內「詞頻 have」與已匹配詞數 count。
3) 當遇到非目標詞，整個視窗重置；當某詞次數超過 need，從左端按 w 收縮直到不超標。
4) 每當 count == m，表示 left 為一個合法起點；記錄後將最左詞彈出，讓視窗繼續向右尋找下一解。
5) 複雜度：時間 O(n)（常數為字典操作與 w 個偏移），空間 O(U)（U 為不同詞數量，≤ m）。
 */
