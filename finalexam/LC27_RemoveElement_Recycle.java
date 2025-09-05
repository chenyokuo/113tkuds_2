// 題目：回收站清單移除指定元素
// 給定整數陣列與指定代碼 val，就地移除所有等於 val 的元素，輸出新長度與更新後前段內容（順序保持）。

import java.io.*;
import java.util.*;

public class LC27_RemoveElement_Recycle {

    public static void main(String[] args) throws Exception {
        FastIn in = new FastIn(System.in);

        Integer nObj = in.nextInt();
        Integer valObj = in.nextInt();
        int n = (nObj == null ? 0 : nObj);
        int val = (valObj == null ? 0 : valObj);

        int[] a = new int[Math.max(n, 0)];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int k = removeElement(a, n, val);

        // 輸出新長度
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

    // 單指針覆寫：只有當 a[i] != val 才寫入 a[write++] = a[i]
    private static int removeElement(int[] a, int n, int val) {
        int write = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] != val) {
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
1. 使用單指針 write，自左向右掃描陣列；若 a[i] != val，將 a[i] 複寫到 a[write] 並遞增 write。
2. 掃描結束後，write 即為新長度；有效結果位於 a[0..write-1]。
時間複雜度 O(n)，空間複雜度 O(1)。
 */
