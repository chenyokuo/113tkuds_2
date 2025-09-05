// 題目：地震速報雙資料源中位數
// 給定兩個已排序的浮點數陣列 A、B，請在不實際合併的情況下回傳它們聯合集的中位數（若總長度為偶數取中間兩數平均）。

import java.io.*;
import java.util.*;

public class LC04_Median_QuakeFeeds {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        Integer mObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);
        int m = (mObj == null ? 0 : mObj);

        double[] A = new double[n];
        double[] B = new double[m];

        for (int i = 0; i < n; i++) {
            A[i] = in.nextDouble();
        }
        for (int j = 0; j < m; j++) {
            B[j] = in.nextDouble();
        }

        double median = findMedianSortedArrays(A, B);
        // 依題意：輸出保留 1 位
        System.out.printf("%.1f%n", median);
    }

    // 核心演算法：在較短陣列上二分切割
    private static double findMedianSortedArrays(double[] A, double[] B) {
        // 確保 A 為較短陣列
        if (A.length > B.length) {
            return findMedianSortedArrays(B, A);
        }

        int n = A.length, m = B.length;
        int total = n + m;
        int half = (total + 1) / 2; // 左半部元素總數

        int lo = 0, hi = n; // i ∈ [0, n]
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;   // A 的切點
            int j = half - i;          // B 的切點

            double Aleft = (i == 0) ? Double.NEGATIVE_INFINITY : A[i - 1];
            double Aright = (i == n) ? Double.POSITIVE_INFINITY : A[i];
            double Bleft = (j == 0) ? Double.NEGATIVE_INFINITY : B[j - 1];
            double Bright = (j == m) ? Double.POSITIVE_INFINITY : B[j];

            // 驗證是否滿足：左半最大 <= 右半最小
            if (Aleft <= Bright && Bleft <= Aright) {
                if ((total & 1) == 1) {
                    // 總長度奇數 → 中位數是左半最大
                    return Math.max(Aleft, Bleft);
                } else {
                    // 總長度偶數 → 取左右邊界中位
                    double leftMax = Math.max(Aleft, Bleft);
                    double rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0;
                }
            } else if (Aleft > Bright) {
                // A 左邊太大 → i 往左
                hi = i - 1;
            } else {
                // B 左邊太大（或 A 右邊太小） → i 往右
                lo = i + 1;
            }
        }

        // 正常情況不會到這行（輸入前提保證兩列已排序且總長度 >= 1）
        throw new IllegalStateException("Partition failed – check input sorting.");
    }

    // 輕量快速輸入：支援跨行讀取、空字串情形
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

        Double nextDouble() throws IOException {
            String t = next();
            if (t == null) {
                return null;
            }
            return Double.parseDouble(t);
        }
    }
}
/*
解題思路：
1. 令 A 為較短陣列，對 A 的切點 i 做二分搜尋；令 j = (n + m + 1) / 2 - i。
2. 當 A[i-1] <= B[j] 且 B[j-1] <= A[i] 時，分割正確：
   - 若總長奇數：取 max(A[i-1], B[j-1])。
   - 若總長偶數：取 (max(A[i-1], B[j-1]) + min(A[i], B[j])) / 2。
3. 以 ±∞ 處理邊界（i 或 j 在 0 / 長度）。
時間複雜度 O(log(min(n,m)))，空間 O(1)。
 */
