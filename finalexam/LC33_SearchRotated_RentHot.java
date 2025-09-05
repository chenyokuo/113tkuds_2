// 題目：旋轉陣列搜尋
// 給定一個被旋轉的升序陣列與目標值 target，請回傳 target 的索引；若不存在則回傳 -1（時間 O(log n)）。

import java.io.*;
import java.util.*;

public class LC33_SearchRotated_RentHot {

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

        System.out.println(search(a, target));
    }

    // 改良二分：每次判斷哪一半有序，決定往哪側縮小
    private static int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >>> 1);
            if (nums[mid] == target) {
                return mid;
            }

            // 左半有序
            if (nums[l] <= nums[mid]) {
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else { // 右半有序
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
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
1. 二分中點 mid；若命中直接回傳。
2. 判斷哪一半有序：
   - 若 nums[l] <= nums[mid]，左半有序：若 target 位於 [nums[l], nums[mid])，往左縮小；否則往右。
   - 否則右半有序：若 target 位於 (nums[mid], nums[r]]，往右；否則往左。
3. 迴圈結束仍未找到則回 -1。
時間 O(log n)，空間 O(1)。
 */
