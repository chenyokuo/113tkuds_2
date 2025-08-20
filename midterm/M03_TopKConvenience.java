import java.io.*;
import java.util.*;

/**
 * 需求：統計近一週門市商品銷量，輸出 Top-K（高→低）。
 * 穩定性說明：
 *   - 以 (qty DESC, name ASC) 為最終排序準則。
 *   - Heap 內部採用「較差者在堆頂」的順序： (qty ASC, name DESC)
 *     如此當新資料「更好」（qty 大；或 qty 相同但 name 較小）時，能替換堆頂。
 */
public class M03_TopKConvenience {
    private static class Item {
        String name;
        int qty;
        Item(String n, int q) { name = n; qty = q; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(readNonEmptyLine(br));
        int n = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // Min-Heap：把「較差」的放上面，便於淘汰
        PriorityQueue<Item> heap = new PriorityQueue<>(new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(a.qty, b.qty); // 小在前
                return -a.name.compareTo(b.name); // 同量時字典序大的視為較差（放前面）
            }
        });

        int read = 0;
        while (read < n) {
            String line = readNonEmptyLine(br);
            st = new StringTokenizer(line);
            String name = st.nextToken();        // 題目保證品名無空白
            int qty = Integer.parseInt(st.nextToken());

            Item cur = new Item(name, qty);
            if (heap.size() < K) {
                heap.offer(cur);
            } else {
                // 若 cur 比堆頂更好（qty 大或同量但 name 較小）→ 取代
                Item top = heap.peek();
                if (isBetter(cur, top)) {
                    heap.poll();
                    heap.offer(cur);
                }
            }
            read++;
        }

        // 取出並依最終規則排序：qty DESC, name ASC
        List<Item> ans = new ArrayList<>(heap);
        Collections.sort(ans, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                if (a.qty != b.qty) return Integer.compare(b.qty, a.qty); // 大在前
                return a.name.compareTo(b.name); // 同量時字典序小在前
            }
        });

        StringBuilder sb = new StringBuilder();
        for (Item it : ans) {
            sb.append(it.name).append(' ').append(it.qty).append('\n');
        }
        System.out.print(sb.toString());
    }

    // cur 是否比 old 更好：qty 大；或 qty 相同但 name 更小（字典序）
    private static boolean isBetter(Item cur, Item old) {
        if (cur.qty != old.qty) return cur.qty > old.qty;
        return cur.name.compareTo(old.name) < 0;
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s.trim();
        }
        return "";
    }
}

/*
 * Time Complexity: O(n log K + K log K)
 * 說明：
 * 1) 對每筆資料在大小為 K 的最小堆做插入/替換，單次 O(log K)，共 n 次 → O(n log K)。
 * 2) 最後將堆中 K 筆做排序輸出 → O(K log K)。
 * 3) 因 K ≪ n，主導項為 O(n log K)；額外空間 O(K)。
 */

