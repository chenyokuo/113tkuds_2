import java.util.*;

public class MeetingRoomScheduler {

    // ----------- Part 1: 最少需要幾間會議室 -----------
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 以開始時間排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        // Min Heap 儲存各會議室的結束時間
        PriorityQueue<Integer> endTimes = new PriorityQueue<>();

        for (int[] interval : intervals) {
            if (!endTimes.isEmpty() && interval[0] >= endTimes.peek()) {
                // 若可重用會議室
                endTimes.poll();
            }
            endTimes.offer(interval[1]);
        }

        return endTimes.size(); // heap 的大小 = 所需會議室數
    }

    // ----------- Part 2: 最大化會議時間給定 N 個會議室 -----------
    public static int maxMeetingTimeWithFixedRooms(int[][] intervals, int rooms) {
        if (intervals == null || intervals.length == 0 || rooms == 0) return 0;

        // 以結束時間排序（貪心）
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

        // 每個會議室維護一個 PriorityQueue：結束時間
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int totalTime = 0;

        for (int[] meeting : intervals) {
            // 回收已完成的會議室
            while (!heap.isEmpty() && heap.peek()[1] <= meeting[0]) {
                heap.poll();
            }

            if (heap.size() < rooms) {
                heap.offer(meeting);
                totalTime += (meeting[1] - meeting[0]);
            }
        }

        return totalTime;
    }

    // =================== 測試主程式 ===================
    public static void main(String[] args) {
        // Part 1 測試
        testMinRooms(new int[][]{{0, 30}, {5, 10}, {15, 20}}, 2);
        testMinRooms(new int[][]{{9, 10}, {4, 9}, {4, 17}}, 2);
        testMinRooms(new int[][]{{1, 5}, {8, 9}, {8, 9}}, 2);

        // Part 2 測試（固定會議室數）
        testMaxTime(new int[][]{{1, 4}, {2, 3}, {4, 6}}, 1, 5); // 選 [1,4] 和 [4,6]
        testMaxTime(new int[][]{{0, 2}, {1, 3}, {2, 5}, {4, 6}}, 2, 6); // [0,2], [2,5], [4,6]
    }

    private static void testMinRooms(int[][] meetings, int expected) {
        int result = minMeetingRooms(meetings);
        System.out.println("最少會議室數: " + result + "，預期: " + expected + " ➤ " + (result == expected ? "✅" : "❌"));
    }

    private static void testMaxTime(int[][] meetings, int rooms, int expected) {
        int result = maxMeetingTimeWithFixedRooms(meetings, rooms);
        System.out.println("最大會議總時長（" + rooms + " 間會議室）: " + result + "，預期: " + expected + " ➤ " + (result == expected ? "✅" : "❌"));
    }
}
