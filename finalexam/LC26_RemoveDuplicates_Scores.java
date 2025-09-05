// 題目：去重學生成績單
// 給定已排序（非遞減）的學號整數陣列，請就地移除重複，使每個值僅保留一次，輸出壓縮後長度與前段內容。

import java.io.*;
import java.util.*;

public class LC26_RemoveDuplicates_Scores {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);

        int[] a = new int[Math.max(n, 0)];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int k = removeDuplicates(a, n);

        // 輸出壓縮後長度
        System.out.println(k);

        // 輸出前段結果（k==0 時不輸出第二行）
        if (k > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < k; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(a[i]);
            }
            System.out.println(sb.toString());
        }
    }

    // 讀寫指標：write 指向可寫位置，掃描時當前值與前一保留不同才寫入
    private static int removeDuplicates(int[] a, int n) {
        if (n == 0) {
            return 0;
        }
        int write = 1;                // a[0] 一定保留
        for (int i = 1; i < n; i++) {
            if (a[i] != a[write - 1]) {
                a[write++] = a[i];
            }
        }
        return write;
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
1. 陣列已排序，使用雙指標：write 指向下個可寫位置。
2. 從 i=1 掃描，若 a[i] 與 a[write-1] 不同，將 a[i] 複寫到 a[write] 並遞增 write。
3. 最終 write 即新長度，且有效結果位於 a[0..write-1]。
時間 O(n)，空間 O(1)。
 */
