// 題目：公告全文搜尋
// 給定主字串 haystack 與子字串 needle，回傳 needle 首次出現於 haystack 的起始索引；若不存在則回傳 -1。

import java.io.*;

public class LC28_StrStr_NoticeSearch {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String haystack = br.readLine();
        if (haystack == null) {
            haystack = "";
        }
        String needle = br.readLine();
        if (needle == null) {
            needle = "";
        }

        System.out.println(strStrKMP(haystack, needle));
    }

    // KMP：時間 O(n+m)，空間 O(m)
    private static int strStrKMP(String s, String p) {
        int n = s.length(), m = p.length();
        if (m == 0) {
            return 0;
        }
        if (m > n) {
            return -1;
        }

        int[] lps = buildLPS(p);
        int i = 0, j = 0; // i 掃 s，j 掃 p
        while (i < n) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m; // 命中

                }
            } else {
                if (j > 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    // 建立部分比對表（Longest Prefix Suffix）
    private static int[] buildLPS(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0; // 當前最長前綴後綴長度
        for (int i = 1; i < m;) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}
/*
解題思路：
1. 邊界處理：若 needle 為空字串，依題意回傳 0；若 needle 長於 haystack，直接回 -1。
2. 使用 KMP：先建立 needle 的 LPS 表（最長可重疊前後綴），匹配時失敗可用 LPS 退回避免重複比對。
3. 掃描 haystack：
   - 相等時 i、j 同進；j 達到長度 m 代表成功，回 i-m。
   - 不等時若 j>0，令 j = lps[j-1]；否則 i++。
時間複雜度 O(n+m)，空間複雜度 O(m)。若資料很小亦可改為暴力 O(nm)。
 */
