import java.io.*;
import java.util.*;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long g = gcd(a, b);
        // 為避免溢位，先除後乘
        long l = a / g * b;

        System.out.println("GCD: " + g);
        System.out.println("LCM: " + l);
    }

    // 遞迴版歐幾里得算法
    private static long gcd(long x, long y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }
}

/*
 * Time Complexity: O(log min(a, b))
 * 說明：
 * 1) 歐幾里得演算法每次呼叫將問題縮小，平均複雜度為 log(min(a,b))。
 * 2) 單次模運算為 O(1)，因此 gcd 遞迴總成本 O(log min(a,b))。
 * 3) LCM 僅需一次除法與一次乘法 O(1)。
 * 整體時間複雜度：O(log min(a,b))，空間為遞迴深度 O(log min(a,b))。
 */
