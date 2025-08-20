import java.io.*;
import java.util.*;

public class M01_BuildHeap {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String type = readNonEmptyLine(br).trim().toLowerCase(); // "max" or "min"
        int n = Integer.parseInt(readNonEmptyLine(br).trim());

        int[] a = new int[n];
        int filled = 0;
        // 可能跨多行，累積讀滿 n 個整數
        while (filled < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && filled < n) {
                a[filled++] = Integer.parseInt(st.nextToken());
            }
        }

        boolean isMax = type.equals("max");
        buildHeap(a, isMax);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i]);
        }
        System.out.println(sb.toString());
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return "";
    }

    // Bottom-up Build-Heap：從最後一個非葉節點開始下沉
    private static void buildHeap(int[] a, boolean isMax) {
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            siftDown(a, i, a.length, isMax);
        }
    }

    // 將索引 i 的元素向下調整至堆序成立
    private static void siftDown(int[] a, int i, int n, boolean isMax) {
        while (true) {
            int left = i * 2 + 1;
            if (left >= n) break;              // 無子節點
            int right = left + 1;

            // 選出「更佳」的子節點：max-heap 選較大者；min-heap 選較小者
            int best = left;
            if (right < n && better(a[right], a[left], isMax)) best = right;

            // 若子節點比父節點更佳，交換並持續下沉
            if (better(a[best], a[i], isMax)) {
                swap(a, i, best);
                i = best;
            } else {
                break;
            }
        }
    }

    // 比較器：對 max-heap 回傳 x>y；對 min-heap 回傳 x<y
    private static boolean better(int x, int y, boolean isMax) {
        return isMax ? (x > y) : (x < y);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}

/*
 * Time Complexity: O(n)
 * 說明：
 * 1) Bottom-up Build-Heap 從 floor(n/2)-1 到 0 逐節點執行 siftDown，
 *    節點位於高度 h 的最多有 ⌈n/2^{h+1}⌉ 個，每次下沉最多 h 步。
 * 2) 總成本上界為 Σ_{h≥0} (n/2^{h+1}) * h = O(n) * Σ_{h≥0} (h/2^{h+1}) = O(n)。
 * 3) 因此整體建堆時間為線性 O(n)，空間為 O(1)（就地建堆）。
 */
