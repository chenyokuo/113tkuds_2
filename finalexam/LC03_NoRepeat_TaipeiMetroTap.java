// 檔名：LC03_NoRepeat_TaipeiMetroTap.java
// 題目：北捷刷卡最長無重複片段（Longest Substring Without Repeating Characters）
// 輸入：一行字串 s（可為空、可含重複字元，假設 ASCII 可見字集，但本解法支援一般 Unicode）
// 輸出：最長不含重複字元之「連續子字串」長度（整數）
//
// 解法：滑動視窗 + 最近位置表 (Map<Character, Integer>)
// 右指針 r 逐步擴張；若 s[r] 先前在視窗內出現過，將左指針 l 推進到「該字元上次出現位置 + 1」。
// 每步更新答案 ans = max(ans, r - l + 1)
// 時間複雜度 O(n)，空間複雜度 O(k)，k 為字元集合大小。

import java.io.*;
import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        if (s == null) {
            s = "";          // 允許空輸入行
        }
        // Map<char, lastIndex>：記錄每個字元「最近一次出現的索引」
        Map<Character, Integer> last = new HashMap<>();

        int ans = 0;
        int l = 0; // 視窗左界

        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);

            // 若 c 曾出現且位置 >= l，代表落在目前視窗內 → 左界推進
            if (last.containsKey(c) && last.get(c) >= l) {
                l = last.get(c) + 1;
            }

            last.put(c, r);                    // 更新最近位置
            ans = Math.max(ans, r - l + 1);    // 視窗長度
        }

        System.out.println(ans);
    }
}
