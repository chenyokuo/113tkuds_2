import java.util.Arrays;

public class SelectionSortImplementation {

    public static void main(String[] args) {
        int[] original = {64, 25, 12, 22, 11};

        System.out.println("=== 原始陣列 ===");
        System.out.println(Arrays.toString(original));

        // 複製陣列分別給兩種排序用
        int[] selectionArray = Arrays.copyOf(original, original.length);
        int[] bubbleArray = Arrays.copyOf(original, original.length);

        System.out.println("\n=== 選擇排序過程 ===");
        int[] result1 = selectionSort(selectionArray);

        System.out.println("\n=== 氣泡排序過程 ===");
        int[] result2 = bubbleSort(bubbleArray);

        System.out.println("\n最終排序結果：");
        System.out.println("選擇排序結果：" + Arrays.toString(selectionArray));
        System.out.println("氣泡排序結果：" + Arrays.toString(bubbleArray));

        System.out.println("\n比較統計：");
        System.out.println("選擇排序 - 比較次數：" + result1[0] + "，交換次數：" + result1[1]);
        System.out.println("氣泡排序 - 比較次數：" + result2[0] + "，交換次數：" + result2[1]);
    }

    // 選擇排序（回傳比較與交換次數）
    public static int[] selectionSort(int[] arr) {
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;

            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                int temp = arr[i];
                arr[i] = arr[minIdx];
                arr[minIdx] = temp;
                swaps++;
            }

            System.out.println("第 " + (i + 1) + " 輪：" + Arrays.toString(arr));
        }

        return new int[]{comparisons, swaps};
    }

    // 氣泡排序（回傳比較與交換次數）
    public static int[] bubbleSort(int[] arr) {
        int n = arr.length;
        int comparisons = 0;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    // 交換
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                }
            }

            System.out.println("第 " + (i + 1) + " 輪：" + Arrays.toString(arr));

            if (!swapped) break; // 若沒交換，提早結束
        }

        return new int[]{comparisons, swaps};
    }
}
