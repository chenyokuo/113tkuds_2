public class RecursiveMathCalculator {

    // 組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
    public static int combination(int n, int k) {
        if (k == 0 || k == n) return 1;
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }

    // 卡塔蘭數 Catalan(n) = Σ Catalan(i) × Catalan(n - 1 - i)
    public static long catalan(int n) {
        if (n <= 1) return 1;
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += catalan(i) * catalan(n - 1 - i);
        }
        return result;
    }

    // 漢諾塔步數 Hanoi(n) = 2 × Hanoi(n-1) + 1
    public static long hanoiSteps(int n) {
        if (n == 1) return 1;
        return 2 * hanoiSteps(n - 1) + 1;
    }

    // 判斷是否為回文數（不轉為字串）
    public static boolean isPalindrome(int n) {
        return n == reverse(n);
    }

    // 輔助：反轉整數
    private static int reverse(int n) {
        int reversed = 0;
        while (n > 0) {
            reversed = reversed * 10 + n % 10;
            n /= 10;
        }
        return reversed;
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴數學計算器 ===");

        int n = 5, k = 2;
        System.out.println("\n1️⃣ 組合數 C(" + n + "," + k + ") = " + combination(n, k));

        int c = 4;
        System.out.println("2️⃣ 第 " + c + " 個卡塔蘭數 = " + catalan(c));

        int h = 3;
        System.out.println("3️⃣ 漢諾塔移動步數 hanoi(" + h + ") = " + hanoiSteps(h));

        int[] testNumbers = {12321, 45654, 1221, 1234};
        for (int num : testNumbers) {
            System.out.println("4️⃣ 數字 " + num + " 是否為回文數？" + isPalindrome(num));
        }
    }
}
