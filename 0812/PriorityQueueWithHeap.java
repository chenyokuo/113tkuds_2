import java.util.*;

public class PriorityQueueWithHeap {
    private static class Task implements Comparable<Task> {
        String name;
        int priority;
        long timestamp; // 為了區分相同優先級的先後

        Task(String name, int priority, long timestamp) {
            this.name = name;
            this.priority = priority;
            this.timestamp = timestamp;
        }

        @Override
        public int compareTo(Task other) {
            // Priority 高者先，若相同則 timestamp 小者先
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            return Long.compare(this.timestamp, other.timestamp);
        }

        @Override
        public String toString() {
            return name + " (Priority: " + priority + ")";
        }
    }

    private PriorityQueue<Task> heap;
    private Map<String, Task> taskMap; // 根據名稱快速查找任務
    private long timestampCounter;

    public PriorityQueueWithHeap() {
        heap = new PriorityQueue<>();
        taskMap = new HashMap<>();
        timestampCounter = 0;
    }

    public void addTask(String name, int priority) {
        if (taskMap.containsKey(name)) {
            System.out.println("任務已存在，請使用 changePriority 修改：" + name);
            return;
        }

        Task task = new Task(name, priority, timestampCounter++);
        heap.offer(task);
        taskMap.put(name, task);
    }

    public String executeNext() {
        if (heap.isEmpty()) return "佇列為空";

        Task next = heap.poll();
        taskMap.remove(next.name);
        return "執行任務：" + next;
    }

    public String peek() {
        if (heap.isEmpty()) return "佇列為空";

        return "下一個任務：" + heap.peek();
    }

    public void changePriority(String name, int newPriority) {
        Task task = taskMap.get(name);
        if (task == null) {
            System.out.println("任務不存在：" + name);
            return;
        }

        // 移除後重新插入（Java PriorityQueue 不支援元素內部屬性更新）
        heap.remove(task); // O(n) operation
        Task updated = new Task(name, newPriority, timestampCounter++);
        heap.offer(updated);
        taskMap.put(name, updated);
    }

    // 測試用 main 方法
    public static void main(String[] args) {
        PriorityQueueWithHeap queue = new PriorityQueueWithHeap();

        queue.addTask("備份", 1);
        queue.addTask("緊急修復", 5);
        queue.addTask("更新", 3);

        System.out.println(queue.peek()); // 緊急修復
        System.out.println(queue.executeNext()); // 緊急修復
        System.out.println(queue.executeNext()); // 更新
        System.out.println(queue.executeNext()); // 備份
        System.out.println(queue.executeNext()); // 佇列為空

        // 測試 changePriority
        queue.addTask("掃描", 2);
        queue.addTask("分析", 3);
        queue.changePriority("掃描", 6);
        System.out.println(queue.peek()); // 掃描
    }
}
