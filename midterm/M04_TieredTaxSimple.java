import java.io.*;
import java.util.*;

public class M04_TieredTaxSimple {
    // 稅率（百分比）
    private static final int R1 = 5;   // 0–120000
    private static final int R2 = 12;  // 120001–500000
    private static final int R3 = 20;  // 500001–1000000
    private static final int R4 = 30;  // 1000001+
    private static final long A = 120_000L, B = 500_000L, C = 1_000_000L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(readNonEmptyLine(br).trim());

        StringBuilder out = new StringBuilder();
        long sumTax = 0;

        for (int i = 0; i < n; i++) {
            long income = Long.parseLong(readNonEmptyLine(br).trim());
            long tax = calcTax(income);
            sumTax += tax;
            out.append("Tax: ").append(tax).append('\n');
        }

        long avg = Math.round(sumTax / (double) n);
        out.append("Average: ").append(avg).append('\n');
        System.out.print(out.toString());
    }

    // 逐段累加（progressive）：對每一段取 min(收入, 上界) - 下界 + 1? → 題意為含下界、自然整數收入，用「區段長度」計算即可
    private static long calcTax(long x) {
        long tax = 0;

        // 第 1 段：0..A
        long part1 = Math.min(x, A);
        tax += roundPct(part1, R1);

        if (x > A) {
            // 第 2 段：(A+1)..B
            long part2 = Math.min(x, B) - A;
            tax += roundPct(part2, R2);
        }
        if (x > B) {
            // 第 3 段：(B+1)..C
            long part3 = Math.min(x, C) - B;
            tax += roundPct(part3, R3);
        }
        if (x > C) {
            // 第 4 段：C+ 以上
            long part4 = x - C;
            tax += roundPct(part4, R4);
        }
        return tax;
    }

    // 將某段金額依百分比取整數（四捨五入）
    private static long roundPct(long amount, int percent) {
        // 使用 double 再四捨五入，避免整數除法截斷
        return Math.round(amount * (percent / 100.0));
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
 * 1) 每筆收入僅固定檢查 4 個級距並做常數次運算 → O(1)。
 * 2) 共處理 n 筆輸入 → 總時間 O(n)；僅用常數額外空間 → O(1)。
 */
