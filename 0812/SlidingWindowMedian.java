import java.util.*;

public class SlidingWindowMedian {
    // Max Heap for smaller half
    private PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
    // Min Heap for larger half
    private PriorityQueue<Integer> right = new PriorityQueue<>();

    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            // 新增新元素
            addNum(nums[i]);

            // 若超出視窗大小，移除舊元素
            if (i >= k) {
                removeNum(nums[i - k]);
            }

            // 當進入完整視窗時，取得中位數
            if (i >= k - 1) {
                result.add(getMedian(k));
            }
        }

        // 轉成 double[]
        double[] resArr = new double[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resArr[i] = result.get(i);
        }
        return resArr;
    }

    // 加入數字到 heap，並維持平衡
    private void addNum(int num) {
        if (left.isEmpty() || num <= left.peek()) {
            left.offer(num);
        } else {
            right.offer(num);
        }
        balanceHeaps();
    }

    // 移除數字
    private void removeNum(int num) {
        if (num <= left.peek()) {
            left.remove(num); // O(n)
        } else {
            right.remove(num); // O(n)
        }
        balanceHeaps();
    }

    // 平衡兩個 heap 的大小
    private void balanceHeaps() {
        if (left.size() > right.size() + 1) {
            right.offer(left.poll());
        } else if (right.size() > left.size()) {
            left.offer(right.poll());
        }
    }

    // 取得中位數
    private double getMedian(int k) {
        if (k % 2 == 1) {
            return (double) left.peek();
        } else {
            return ((double) left.peek() + right.peek()) / 2.0;
        }
    }

    // =================== 測試主程式 ===================
    public static void main(String[] args) {
        SlidingWindowMedian solver = new SlidingWindowMedian();

        test(solver, new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3,
                new double[]{1, -1, -1, 3, 5, 6});
        test(solver, new int[]{1, 2, 3, 4}, 2,
                new double[]{1.5, 2.5, 3.5});
    }

    private static void test(SlidingWindowMedian solver, int[] nums, int k, double[] expected) {
        System.out.println("輸入: " + Arrays.toString(nums) + ", K = " + k);
        double[] result = solver.medianSlidingWindow(nums, k);
        System.out.println("輸出: " + Arrays.toString(result));
        System.out.println("預期: " + Arrays.toString(expected));
        System.out.println("通過: " + Arrays.equals(result, expected));
        System.out.println("------------------------------------------------");
    }
}
