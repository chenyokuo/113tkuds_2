// 題目：防災物資組合總和（Combination Sum I）
// 給定物資價格清單與預算 target，價格可重複使用多次，列出所有升序組合（組內遞增）。重複組合需去除。

import java.io.*;
import java.util.*;

public class LC39_CombinationSum_PPE {

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
        dfs(a, 0, target, path, ans);

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

    // 回溯：可重複使用同一數字 → 下一層仍從 i 開始；同層跳過重複值以去重
    private static void dfs(int[] a, int start, int remain, ArrayList<Integer> path, List<List<Integer>> ans) {
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
            dfs(a, i, remain - v, path, ans); // 可重複使用 → 傳 i
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
