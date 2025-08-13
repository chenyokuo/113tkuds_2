import java.util.*;

public class MovingAverageStream {
    private int size;
    private Queue<Integer> window;
    private long sum;

    // 中位數用的兩個heap
    private PriorityQueue<Integer> maxHeap;
    private PriorityQueue<Integer> minHeap;

    // 用於延遲刪除的map
    private Map<Integer, Integer> delayed;

    // 單調隊列維護 min 和 max
    private Deque<Integer> minDeque;
    private Deque<Integer> maxDeque;

    public MovingAverageStream(int size) {
        this.size = size;
        window = new LinkedList<>();
        sum = 0;
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
        delayed = new HashMap<>();
        minDeque = new LinkedList<>();
        maxDeque = new LinkedList<>();
    }

    public double next(int val) {
        window.offer(val);
        sum += val;

        // 添加新值到中位數heap
        if (maxHeap.isEmpty() || val <= maxHeap.peek()) {
            maxHeap.offer(val);
        } else {
            minHeap.offer(val);
        }
        balanceHeaps();

        // 添加新值到minDeque（保持單調遞增）
        while (!minDeque.isEmpty() && minDeque.peekLast() > val) {
            minDeque.pollLast();
        }
        minDeque.offerLast(val);

        // 添加新值到maxDeque（保持單調遞減）
        while (!maxDeque.isEmpty() && maxDeque.peekLast() < val) {
            maxDeque.pollLast();
        }
        maxDeque.offerLast(val);

        // 若視窗超過size，移除最舊元素
        if (window.size() > size) {
            int old = window.poll();
            sum -= old;

            // 延遲刪除
            delayed.put(old, delayed.getOrDefault(old, 0) + 1);

            // 從中位數heap剔除old元素
            if (!maxHeap.isEmpty() && old <= maxHeap.peek()) {
                pruneHeap(maxHeap);
            } else {
                pruneHeap(minHeap);
            }
            balanceHeaps();

            // 從minDeque移除
            if (!minDeque.isEmpty() && minDeque.peekFirst() == old) {
                minDeque.pollFirst();
            }
            // 從maxDeque移除
            if (!maxDeque.isEmpty() && maxDeque.peekFirst() == old) {
                maxDeque.pollFirst();
            }
        }

        return (double) sum / window.size();
    }

    // 平衡兩個heap大小
    private void balanceHeaps() {
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    // 清理heap中延遲刪除的元素
    private void pruneHeap(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            if (delayed.containsKey(num)) {
                delayed.put(num, delayed.get(num) - 1);
                if (delayed.get(num) == 0) delayed.remove(num);
                heap.poll();
            } else {
                break;
            }
        }
    }

    // 取得當前視窗中位數
    public double getMedian() {
        if (window.isEmpty()) return 0.0;
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    // 取得視窗最小值
    public int getMin() {
        if (minDeque.isEmpty()) throw new NoSuchElementException("視窗為空");
        return minDeque.peekFirst();
    }

    // 取得視窗最大值
    public int getMax() {
        if (maxDeque.isEmpty()) throw new NoSuchElementException("視窗為空");
        return maxDeque.peekFirst();
    }

    // =================== 測試 ===================
    public static void main(String[] args) {
        MovingAverageStream ma = new MovingAverageStream(3);
        System.out.printf("next(1) = %.2f\n", ma.next(1)); // 1.0
        System.out.printf("next(10) = %.2f\n", ma.next(10)); // 5.5
        System.out.printf("next(3) = %.2f\n", ma.next(3)); // 4.67
        System.out.printf("next(5) = %.2f\n", ma.next(5)); // 6.0
        System.out.printf("getMedian() = %.2f\n", ma.getMedian()); // 5.0
        System.out.println("getMin() = " + ma.getMin()); // 3
        System.out.println("getMax() = " + ma.getMax()); // 10
    }
}
