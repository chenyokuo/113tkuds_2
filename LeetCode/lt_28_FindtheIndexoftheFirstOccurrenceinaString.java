// 題目：Find the Index of the First Occurrence in a String (strStr)
// 給定字串 haystack 與 needle，回傳 needle 在 haystack 中第一次出現的起始索引；若不存在則回傳 -1。

class Solution {

    public int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) {
            return 0;   // 保底處理（雖然本題 m≥1）

        }
        if (m > n) {
            return -1;
        }

        // 建立 KMP 的最長前綴後綴表（lps）
        int[] lps = buildLps(needle);

        // i 指向 haystack，j 指向 needle
        int i = 0, j = 0;
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) {
                    return i - m; // 成功匹配，回傳起始索引

                }
            } else {
                // 發生不匹配：若 j>0，回退到 lps[j-1]；否則 i 前進
                if (j > 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return -1; // 掃描完未找到
    }

    // 建立 KMP 的 lps 陣列：lps[x] = pattern[0..x] 的「最長真前綴 = 真後綴」長度
    private int[] buildLps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0;   // 目前匹配到的前綴長度
        int i = 1;     // 從第 2 個字元開始計算

        while (i < m) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;   // 延伸匹配
            } else if (len > 0) {
                len = lps[len - 1]; // 回退到較短可用前綴
            } else {
                lps[i++] = 0;       // 無可用前綴
            }
        }
        return lps;
    }
}

/*
解題思路：
1. 使用 KMP 演算法。先為 needle 建立 lps（Longest Prefix Suffix）表，
   lps[i] 代表 pattern[0..i] 的最長「真前綴=真後綴」長度。
2. 以雙指標 i（haystack）、j（needle）掃描：
   - 若字符相同，同步前進；當 j==m 時回傳 i-m。
   - 若不相同：若 j>0，將 j 回退到 lps[j-1]；否則 i++。
3. 複雜度：時間 O(n+m)，空間 O(m)。
 */
