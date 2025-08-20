import java.io.*;
import java.util.*;

public class M02_YouBikeNextArrival {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(readNonEmptyLine(br).trim());
        int[] t = new int[n]; // 以自 00:00 起的總分鐘數儲存

        for (int i = 0; i < n; i++) {
            t[i] = parseHHMM(readNonEmptyLine(br).trim());
        }

        int q = parseHHMM(readNonEmptyLine(br).trim());

        // upper_bound：第一個 > q 的索引
        int idx = upperBound(t, q);
        if (idx == n) {
            System.out.println("No bike");
        } else {
            System.out.println(toHHMM(t[idx]));
        }
    }

    private static String readNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            if (!s.trim().isEmpty()) return s;
        }
        return ""; // 理論上不會到這
    }

    private static int parseHHMM(String s) {
        // s 形如 "HH:mm"
        int colon = s.indexOf(':');
        int hh = Integer.parseInt(s.substring(0, colon));
        int mm = Integer.parseInt(s.substring(colon + 1));
        return hh * 60 + mm;
    }

    private static String toHHMM(int minutes) {
        int hh = minutes / 60;
        int mm = minutes % 60;
        return String.format("%02d:%02d", hh, mm);
    }

    // 傳回第一個 > key 的位置（upper_bound）
    private static int upperBound(int[] a, int key) {
        int lo = 0, hi = a.length; // [lo, hi)
        while (lo < hi) {
            int mid = lo + ((hi - lo) >>> 1);
            if (a[mid] <= key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo; // 若全都 <= key，回傳 a.length
    }
}
