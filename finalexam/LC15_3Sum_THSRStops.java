// 題目：高鐵站點三元組 3Sum
// 給定整數陣列，請輸出所有不重複且升序排列、總和為 0 的三元組。

import java.io.*;
import java.util.*;

public class LC15_3Sum_THSRStops {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        List<int[]> ans = threeSum(a);

        StringBuilder sb = new StringBuilder();
        for (int[] t : ans) {
            sb.append(t[0]).append(' ').append(t[1]).append(' ').append(t[2]).append('\n');
        }
        System.out.print(sb.toString()); // 無解時不輸出任何行
    }

    // 排序 + 固定 i + 兩指針；去重
    private static List<int[]> threeSum(int[] nums) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 跳過重複的首元素

            }
            if (nums[i] > 0) {
                break;                         // 後面都 >= nums[i] > 0，無解
            }
            int l = i + 1, r = n - 1;
            int target = -nums[i];

            while (l < r) {
                int sumLR = nums[l] + nums[r];
                if (sumLR == target) {
                    res.add(new int[]{nums[i], nums[l], nums[r]});
                    // 同步略過重複
                    int lv = nums[l], rv = nums[r];
                    while (l < r && nums[l] == lv) {
                        l++;
                    }
                    while (l < r && nums[r] == rv) {
                        r--;
                    }
                } else if (sumLR < target) {
                    l++;
                } else {
                    r--;
                }
            }
        }
        return res;
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
1. 先排序；枚舉 i（固定第一個值），其餘用雙指針 l、r 尋找和為 -nums[i] 的配對。
2. 若 nums[i] > 0 可提前結束；對 i、l、r 都要適當「去重」以避免重複三元組。
3. 每次命中後同步跳過相同的 l 與 r 值。
時間複雜度 O(n^2)，空間 O(1)（不含輸出）。
 */
