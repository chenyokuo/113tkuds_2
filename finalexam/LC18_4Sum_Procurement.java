// 題目：採購限額 4Sum
// 給定整數陣列與目標 target，輸出所有不重複且升序的四元組，其總和為 target。

import java.io.*;
import java.util.*;

public class LC18_4Sum_Procurement {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        Long targetObj = in.nextLong();
        int n = (nObj == null ? 0 : nObj);
        long target = (targetObj == null ? 0L : targetObj);

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        List<int[]> ans = fourSum(a, target);

        StringBuilder sb = new StringBuilder();
        for (int[] t : ans) {
            sb.append(t[0]).append(' ')
                    .append(t[1]).append(' ')
                    .append(t[2]).append(' ')
                    .append(t[3]).append('\n');
        }
        System.out.print(sb.toString()); // 無解時不輸出任何行
    }

    // 雙層枚舉 + 兩指針；嚴格去重與長整數和避免溢位
    private static List<int[]> fourSum(int[] nums, long target) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去重 i
            }
            // 可能的最小/最大和剪枝（以 long 計算避免溢位）
            long min1 = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (min1 > target) {
                break;
            }
            long max1 = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            if (max1 < target) {
                continue;
            }

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // 去重 j
                }
                long min2 = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (min2 > target) {
                    break;
                }
                long max2 = (long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2];
                if (max2 < target) {
                    continue;
                }

                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[r];
                    if (sum == target) {
                        res.add(new int[]{nums[i], nums[j], nums[l], nums[r]});
                        int lv = nums[l], rv = nums[r];
                        while (l < r && nums[l] == lv) {
                            l++; // 去重 L

                        }
                        while (l < r && nums[r] == rv) {
                            r--; // 去重 R

                        }
                    } else if (sum < target) {
                        l++;
                    } else {
                        r--;
                    }
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
1. 先排序；雙層枚舉 i、j，內層用雙指針 l、r 搜尋使 a[i]+a[j]+a[l]+a[r]==target。
2. 嚴格去重：i、j 遇到重複值直接跳過；命中後同步跳過 l、r 的重複值。
3. 剪枝：對每個 i、j 以當前最小/最大可能和進行早停或跳過，提高效率。
時間複雜度 O(n^3)，空間 O(1)（不含輸出）。為避免溢位，和以 long 計算。
 */
