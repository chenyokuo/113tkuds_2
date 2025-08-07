import java.util.Arrays;

public class AdvancedArrayRecursion {

    // 1️⃣ 遞迴快速排序
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(arr, low, high);
            quickSort(arr, low, pivotIdx - 1);
            quickSort(arr, pivotIdx + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 2️⃣ 遞迴合併兩個已排序陣列
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        return mergeHelper(a, 0, b, 0);
    }

    private static int[] mergeHelper(int[] a, int i, int[] b, int j) {
        if (i == a.length) return Arrays.copyOfRange(b, j, b.length);
        if (j == b.length) return Arrays.copyOfRange(a, i, a.length);

        if (a[i] < b[j]) {
            return prepend(a[i], mergeHelper(a, i + 1, b, j));
        } else {
            return prepend(b[j], mergeHelper(a, i, b, j + 1));
        }
    }

    private static int[] prepend(int val, int[] arr) {
        int[] result = new int[arr.length + 1];
        result[0] = val;
        System.arraycopy(arr, 0, result, 1, arr.length);
        return result;
    }

    // 3️⃣ 遞迴尋找第 k 小元素（Quickselect）
    public static int findKthSmallest(int[] arr, int k) {
        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }

    private static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high) return arr[low];

        int pivotIdx = partition(arr, low, high);
        if (k == pivotIdx) return arr[k];
        else if (k < pivotIdx) return quickSelect(arr, low, pivotIdx - 1, k);
        else return quickSelect(arr, pivotIdx + 1, high, k);
    }

    // 4️⃣ 遞迴檢查是否存在子序列總和等於目標值
    public static boolean subsetSumExists(int[] arr, int target) {
        return subsetSumHelper(arr, 0, target);
    }

    private static boolean subsetSumHelper(int[] arr, int index, int remaining) {
        if (remaining == 0) return true;
        if (index >= arr.length || remaining < 0) return false;

        // include 或不 include 當前元素
        return subsetSumHelper(arr, index + 1, remaining - arr[index])
            || subsetSumHelper(arr, index + 1, remaining);
    }

    // 主程式
    public static void main(String[] args) {
        int[] arr = {7, 2, 9, 4, 1};
        System.out.println("=== 原始陣列 ===");
        System.out.println(Arrays.toString(arr));

        // 快速排序
        int[] quickSorted = Arrays.copyOf(arr, arr.length);
        quickSort(quickSorted, 0, quickSorted.length - 1);
        System.out.println("\n1️⃣ 快速排序結果：");
        System.out.println(Arrays.toString(quickSorted));

        // 合併兩個已排序陣列
        int[] a = {1, 3, 5};
        int[] b = {2, 4, 6};
        int[] merged = mergeSortedArrays(a, b);
        System.out.println("\n2️⃣ 合併排序後：");
        System.out.println(Arrays.toString(merged));

        // 找第 k 小
        int[] kthTest = {7, 2, 9, 4, 1};
        int k = 3;
        int kth = findKthSmallest(kthTest, k);
        System.out.println("\n3️⃣ 第 " + k + " 小的元素是：" + kth);

        // 子集總和
        int target = 10;
        boolean hasSubset = subsetSumExists(arr, target);
        System.out.println("\n4️⃣ 是否存在子序列總和為 " + target + "？" + hasSubset);
    }
}
