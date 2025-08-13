public class ValidMaxHeapChecker {

    public static boolean isValidMaxHeap(int[] arr) {
        int n = arr.length;

        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[i] < arr[left]) {
                System.out.println("違反規則的索引: " + left);
                return false;
            }

            if (right < n && arr[i] < arr[right]) {
                System.out.println("違反規則的索引: " + right);
                return false;
            }
        }

        return true;
    }

    // 測試用 main 方法
    public static void main(String[] args) {
        test(new int[]{100, 90, 80, 70, 60, 75, 65}); // true
        test(new int[]{100, 90, 80, 95, 60, 75, 65}); // false，索引3
        test(new int[]{50});                         // true
        test(new int[]{});                           // true
    }

    private static void test(int[] arr) {
        System.out.print("陣列: ");
        for (int n : arr) System.out.print(n + " ");
        System.out.println();

        boolean isValid = isValidMaxHeap(arr);
        System.out.println("是否為有效 Max Heap: " + isValid);
        System.out.println("--------------------------");
    }
}
