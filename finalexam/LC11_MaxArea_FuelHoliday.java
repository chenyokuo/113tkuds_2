// 題目：連假油量促銷最大區間
// 給定整數陣列 heights，選擇兩個指標 i<j，使 (j-i) * min(heights[i], heights[j]) 最大，回傳此最大值。

import java.io.*;
import java.util.*;

public class LC11_MaxArea_FuelHoliday {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);

        long[] h = new long[n];
        for (int i = 0; i < n; i++) {
            h[i] = in.nextLong();
        }

        System.out.println(maxArea(h));
    }

    // 夾逼雙指針：唯有提升「短邊」才可能使面積變大 → 移動較短邊
    private static long maxArea(long[] h) {
        int l = 0, r = h.length - 1;
        long ans = 0;

        while (l < r) {
            long height = Math.min(h[l], h[r]);
            long width = (long) (r - l);
            ans = Math.max(ans, height * width);

            if (h[l] < h[r]) {
                l++;
            } else if (h[l] > h[r]) {
                r--;
            } else {
                // 相等時移動任一端；此處選擇左端
                l++;
            }
        }
        return ans;
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

        Long nextLong() throws IOException {
            String t = next();
            if (t == null) {
                return null;
            }
            return Long.parseLong(t);
        }
    }
}
/*
解題思路：
1. 以左右指針 l、r 夾逼，面積 = (r-l) * min(h[l], h[r])，每步更新最大值。
2. 只移動較短邊（因為移動較長邊只會使高度下界不升、寬度變小，無益於變大）。
3. 相等時可任意移動一端以繼續搜索。
時間複雜度 O(n)，空間 O(1)。高度與距離乘積以 long 計算，避免溢位。
 */
