// 題目：延誤等級首末定位
// 給定已排序（非遞減）的整數陣列與目標值 target，回傳 target 首次與最後一次出現的索引；若不存在則輸出 -1 -1。

import java.io.*;
import java.util.*;

public class LC34_SearchRange_DelaySpan {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        Integer targetObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);
        int target = (targetObj == null ? 0 : targetObj);

        int[] a = new int[Math.max(n, 0)];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        if (n == 0) {
            System.out.println("-1 -1");
            return;
        }

        int left = lowerBound(a, n, target);
        int right = upperBound(a, n, target) - 1;

        if (left >= n || left > right || a[left] != target) {
            System.out.println("-1 -1");
        } else {
            System.out.println(left + " " + right);
        }
    }

    // 回傳第一個 >= target 的索引；若皆小於 target，回 n
    private static int lowerBound(int[] a, int n, int target) {
        int l = 0, r = n;
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            if (a[m] >= target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    // 回傳第一個 > target 的索引；若皆 <= target，回 n
    private static int upperBound(int[] a, int n, int target) {
        int l = 0, r = n;
        while (l < r) {
            int m = l + ((r - l) >>> 1);
            if (a[m] > target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    // 輕量快速輸入
    private static class FastIn {

        private final BufferedReader br;
        private StringTokenizer st;

        FastIn(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return null;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        Integer nextInt() throws IOException {
            String t = next();
            if (t == null) {
                return null;
            }
            return Integer.parseInt(t);
        }
    }
}
/*
解題思路：
1. 使用兩次二分搜尋：
   - left = lower_bound(target)：第一個 >= target 的位置。
   - right = upper_bound(target) - 1：最後一個 <= target 的位置。
2. 若 left 超界或 a[left] != target，表示不存在，輸出 -1 -1；否則輸出 left right。
時間 O(log n)，空間 O(1)。
 */
