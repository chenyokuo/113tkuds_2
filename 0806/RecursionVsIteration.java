
public class RecursionVsIteration {

    // === 1️⃣ 二項式係數 ===
    public static int binomialRecursive(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomialRecursive(n - 1, k - 1) + binomialRecursive(n - 1, k);
    }

    public static int binomialIterative(int n, int k) {
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= Math.min(i, k); j++) {
                if (j == 0 || j == i) dp[i][j] = 1;
                else dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
            }
        }
        return dp[n][k];
    }

    // === 2️⃣ 陣列所有元素乘積 ===
    public static int productRecursive(int[] arr, int index) {
        if (index == arr.length) return 1;
        return arr[index] * productRecursive(arr, index + 1);
    }

    public static int productIterative(int[] arr) {
        int product = 1;
        for (int val : arr) product *= val;
        return product;
    }

    // === 3️⃣ 元音字母數量 ===
    public static int vowelCountRecursive(String s, int index) {
        if (index == s.length()) return 0;
        char c = Character.toLowerCase(s.charAt(index));
        int add = "aeiou".indexOf(c) >= 0 ? 1 : 0;
        return add + vowelCountRecursive(s, index + 1);
    }

    public static int vowelCountIterative(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if ("aeiou".indexOf(Character.toLowerCase(c)) >= 0)
                count++;
        }
        return count;
    }

    // === 4️⃣ 括號配對 ===
    public static boolean isBalancedRecursive(String s) {
        return isBalancedHelper(s, 0, 0);
    }

    private static boolean isBalancedHelper(String s, int index, int open) {
        if (open < 0) return false;
        if (index == s.length()) return open == 0;

        char c = s.charAt(index);
        if (c == '(') return isBalancedHelper(s, index + 1, open + 1);
        else if (c == ')') return isBalancedHelper(s, index + 1, open - 1);
        else return isBalancedHelper(s, index + 1, open);
    }

    public static boolean isBalancedIterative(String s) {
        int open = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')') open--;
            if (open < 0) return false;
        }
        return open == 0;
    }

    // === 主程式 ===
    public static void main(String[] args) {
        int n = 5, k = 2;
        int[] arr = {2, 3, 4};
        String text = "Recursion is useful!";
        String brackets = "(a(b)c)((()))";

        System.out.println("=== 1️⃣ 二項式係數 C(" + n + "," + k + ") ===");
        System.out.println("遞迴： " + binomialRecursive(n, k));
        System.out.println("迭代： " + binomialIterative(n, k));

        System.out.println("\n=== 2️⃣ 陣列元素乘積 ===");
        System.out.println("遞迴： " + productRecursive(arr, 0));
        System.out.println("迭代： " + productIterative(arr));

        System.out.println("\n=== 3️⃣ 字串元音數量 ===");
        System.out.println("遞迴： " + vowelCountRecursive(text, 0));
        System.out.println("迭代： " + vowelCountIterative(text));

        System.out.println("\n=== 4️⃣ 括號配對 ===");
        System.out.println("遞迴： " + isBalancedRecursive(brackets));
        System.out.println("迭代： " + isBalancedIterative(brackets));
    }
}
