// 題目：防災物資組合總和 II（Combination Sum II）
// 給定物資價格清單與預算 target，每個價格只能用一次，需去除重複組合（數值集合相同視為同一組）。

import java.io.*;
import java.util.*;

public class LC40_CombinationSum2_Procurement {

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

        Arrays.sort(a);

        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        dfs2(a, 0, target, path, ans);

        // 每行輸出一個組合（無解時不輸出任何行）
        StringBuilder sb = new StringBuilder();
        for (List<Integer> comb : ans) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(comb.get(i));
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }

    // 回溯：每個元素最多使用一次 → 下一層從 i+1；同層跳過重複值避免重複組合
    private static void dfs2(int[] a, int start, int remain, ArrayList<Integer> path, List<List<Integer>> ans) {
        if (remain == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < a.length; i++) {
            if (i > start && a[i] == a[i - 1]) {
                continue; // 同層去重

            }
            int v = a[i];
            if (v > remain) {
                break; // 剪枝：已排序

            }
            path.add(v);
            dfs2(a, i + 1, remain - v, path, ans); // 單次使用 → 傳 i+1
            path.remove(path.size() - 1);
        }
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
