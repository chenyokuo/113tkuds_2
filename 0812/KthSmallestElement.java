import java.util.*;

public class KthSmallestElement {

    // 方法1：使用 Max Heap，保留前 K 小的元素
    public static int kthSmallestUsingMaxHeap(int[] nums, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            maxHeap.offer(num);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // 移除最大的
            }
        }

        return maxHeap.peek();
    }

    // 方法2：使用 Min Heap，提取 K 次
    public static int kthSmallestUsingMinHeap(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
        }

        int kth = -1;
        for (int i = 0; i < k; i++) {
            kth = minHeap.poll();
        }

        return kth;
    }

    // =================== 測試用主程式 ===================
    public static void main(String[] args) {
        test(new int[]{7, 10, 4, 3, 20, 15}, 3, 7);
        test(new int[]{1}, 1, 1);
        test(new int[]{3, 1, 4, 1, 5, 9, 2, 6}, 4, 3);
    }

    private static void test(int[] arr, int k, int expected) {
        System.out.println("陣列: " + Arrays.toString(arr));
        System.out.println("K = " + k);

        int result1 = kthSmallestUsingMaxHeap(arr, k);
        int result2 = kthSmallestUsingMinHeap(arr, k);

        System.out.println("方法1（Max Heap）答案: " + result1 + (result1 == expected ? " ✅" : " ❌"));
        System.out.println("方法2（Min Heap）答案: " + result2 + (result2 == expected ? " ✅" : " ❌"));
        System.out.println("------------------------------------------------");
    }
}
