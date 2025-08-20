import java.util.*;

public class M11_HeapSortWithTie {
    static class Pair {
        int score;
        int index; // 原始輸入位置

        Pair(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }

    // 交換工具
    private static void swap(Pair[] arr, int i, int j) {
        Pair tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 下沉操作（Max-Heap）
    private static void heapify(Pair[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // 規則：分數大者優先；若分數相同，index 較小者優先
        if (l < n && compare(arr[l], arr[largest]) > 0) {
            largest = l;
        }
        if (r < n && compare(arr[r], arr[largest]) > 0) {
            largest = r;
        }
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // 比較器：回傳 >0 表示 a > b
    private static int compare(Pair a, Pair b) {
        if (a.score != b.score) return a.score - b.score;
        return b.index - a.index; // index 小者優先 => 視為大
    }

    public static void heapSort(Pair[] arr, int n) {
        // 建立 Max-Heap：O(n)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 一一取出最大值放到尾端：O(n log n)
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Pair[] arr = new Pair[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Pair(sc.nextInt(), i);
        }

        heapSort(arr, n);

        // 輸出遞增序
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i].score);
            if (i < n - 1) System.out.print(" ");
        }
    }
}

/*
計算複雜度：
1. 建堆：O(n)
2. HeapSort：取 n 次，每次下沉 O(log n)，總計 O(n log n)
總複雜度：O(n log n)，空間複雜度 O(1)（就地排序）
*/
