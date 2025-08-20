import java.io.*;
import java.util.*;

public class M10_RBPropertiesCheck {
    static final int BH_INVALID = -1; // 黑高度不一致的標記

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(readNonEmptyLine(br).trim());

        // 讀取 n 組 (val, color)，允許跨行
        int[] val = new int[n];
        char[] col = new char[n]; // 'B' 或 'R'；對於 val=-1（null）顏色視為 'B'
        int filled = 0;
        ArrayDeque<String> buf = new ArrayDeque<>();
        while (filled < n) {
            if (buf.size() < 2) {
                String line = br.readLine();
                if (line == null) break;
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) buf.addLast(st.nextToken());
                continue;
            }
            int v = Integer.parseInt(buf.removeFirst());
            char c = 'B';
            if (!buf.isEmpty()) c = normalizeColor(buf.removeFirst());
            val[filled] = v;
            col[filled] = (v == -1 ? 'B' : c); // 空節點視為黑
            filled++;
        }

        // 1) 根節點必為黑
        if (n > 0) {
            if (val[0] == -1) {
                // 空樹：可視為合法紅黑樹
                System.out.println("RB Valid");
                return;
            }
            if (col[0] != 'B') {
                System.out.println("RootNotBlack");
                return;
            }
        }

        // 2) 不得紅紅相鄰：掃描每個紅色節點，檢它的左右孩子是否為紅
        for (int i = 0; i < n; i++) {
            if (i >= n || val[i] == -1) continue;
            if (col[i] == 'R') {
                int l = 2*i + 1, r = 2*i + 2;
                if (l < n && val[l] != -1 && col[l] == 'R') {
                    System.out.println("RedRedViolation at index " + l);
                    return;
                }
                if (r < n && val[r] != -1 && col[r] == 'R') {
                    System.out.println("RedRedViolation at index " + r);
                    return;
                }
            }
        }

        // 3) 黑高度一致：後序計算黑高度；不一致回傳 BH_INVALID
        int bh = blackHeight(val, col, 0, n);
        if (bh == BH_INVALID) {
            System.out.println("BlackHeightMismatch");
        } else {
            System.out.println("RB Valid");
        }
    }

    // 後序回傳黑高度；若任何一側不一致則回傳 BH_INVALID
    // 定義：NIL(越界或 val=-1) 回傳 1（NIL 視為黑葉並計入黑高度）
    // 一般節點：若為黑色 +1；紅色 +0
    private static int blackHeight(int[] val, char[] col, int i, int n) {
        if (i >= n || val[i] == -1) return 1;         // NIL
        int l = blackHeight(val, col, 2*i + 1, n);
        if (l == BH_INVALID) return BH_INVALID;
        int r = blackHeight(val, col, 2*i + 2, n);
        if (r == BH_INVALID) return BH_INVALID;
        if (l != r) return BH_INVALID;                // 左右黑高度須相等
        return l + (col[i] == 'B' ? 1 : 0);
    }

    private static char normalizeColor(String s) {
        if (s == null || s.isEmpty()) return 'B';
        char c = Character.toUpperCase(s.charAt(0));
        return (c == 'R') ? 'R' : 'B';
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return "";
    }
}

/*
 * Time Complexity: O(n)
 * 說明：
 * 1) 讀入並標準化 n 個節點：O(n)。
 * 2) 紅紅相鄰掃描，每節點最多檢兩個孩子：O(n)。
 * 3) 黑高度後序遞迴，每節點恰訪問一次：O(n)。
 * 整體時間 O(n)；空間為遞迴深度 O(h)，最壞 O(n)，平衡時 O(log n)。
 */
