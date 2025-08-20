import java.io.*;
import java.util.*;

public class M12_MergeKTimeTables {
    static class Node {
        int t;      // minutes since 00:00
        int li;     // which list
        int idx;    // index within that list
        Node(int t, int li, int idx) { this.t = t; this.li = li; this.idx = idx; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int K = Integer.parseInt(readNonEmptyLine(br).trim());
        List<int[]> lists = new ArrayList<>(K);
        boolean hasClockFormat = false; // 若輸入含 ':'，最後以 HH:mm 輸出

        for (int k = 0; k < K; k++) {
            int len = Integer.parseInt(readNonEmptyLine(br).trim());
            int[] arr = new int[len];
            int filled = 0;
            while (filled < len) {
                String line = br.readLine();
                if (line == null) break;
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens() && filled < len) {
                    String tok = st.nextToken();
                    if (tok.indexOf(':') >= 0) hasClockFormat = true;
                    arr[filled++] = parseToMinutes(tok);
                }
            }
            lists.add(arr);
        }

        // Min-Heap by time
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.t));
        for (int i = 0; i < K; i++) {
            int[] a = lists.get(i);
            if (a.length > 0) pq.offer(new Node(a[0], i, 0));
        }

        StringBuilder out = new StringBuilder();
        boolean first = true;
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (!first) out.append(' ');
            first = false;
            out.append(hasClockFormat ? toHHMM(cur.t) : Integer.toString(cur.t));

            int[] src = lists.get(cur.li);
            int next = cur.idx + 1;
            if (next < src.length) {
                pq.offer(new Node(src[next], cur.li, next));
            }
        }

        System.out.println(out.toString());
    }

    private static int parseToMinutes(String s) {
        s = s.trim();
        int p = s.indexOf(':');
        if (p < 0) return Integer.parseInt(s); // already minutes
        int hh = Integer.parseInt(s.substring(0, p));
        int mm = Integer.parseInt(s.substring(p + 1));
        return hh * 60 + mm;
    }

    private static String toHHMM(int minutes) {
        int hh = minutes / 60, mm = minutes % 60;
        return String.format("%02d:%02d", hh, mm);
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
 * Time Complexity: O(T log K)
 * 說明：
 * 1) 每條列表已排序，總元素數 T = Σ len_i（T ≤ 5*200=1000；一般化仍適用）。
 * 2) Min-Heap 大小至多 K（K ≤ 5）。每次彈出與推入皆 O(log K)。
 * 3) 合併過程處理每個元素一次 → 總時間 O(T log K)，空間 O(K)。
 */
