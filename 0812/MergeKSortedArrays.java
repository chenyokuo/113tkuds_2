import java.util.*;

public class MergeKSortedArrays {

    // 包裝元素類
    private static class Element implements Comparable<Element> {
        int value;
        int arrayIndex;    // 來源陣列編號
        int elementIndex;  // 在來源陣列中的索引

        public Element(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }

        @Override
        public int compareTo(Element other) {
            return Integer.compare(this.value, other.value);
        }
    }

    public static List<Integer> mergeKSortedArrays(int[][] arrays) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Element> minHeap = new PriorityQueue<>();

        // 將每個陣列的第一個元素加入 heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.offer(new Element(arrays[i][0], i, 0));
            }
        }

        // 每次從 heap 中取出最小值，並加入下一個元素
        while (!minHeap.isEmpty()) {
            Element curr = minHeap.poll();
            result.add(curr.value);

            int nextIndex = curr.elementIndex + 1;
            if (nextIndex < arrays[curr.arrayIndex].length) {
                int nextValue = arrays[curr.arrayIndex][nextIndex];
                minHeap.offer(new Element(nextValue, curr.arrayIndex, nextIndex));
            }
        }

        return result;
    }

    // =================== 測試主程式 ===================
    public static void main(String[] args) {
        test(new int[][]{{1, 4, 5}, {1, 3, 4}, {2, 6}}, Arrays.asList(1, 1, 2, 3, 4, 4, 5, 6));
        test(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        test(new int[][]{{1}, {0}}, Arrays.asList(0, 1));
    }

    private static void test(int[][] arrays, List<Integer> expected) {
        List<Integer> result = mergeKSortedArrays(arrays);
        System.out.println("輸出:   " + result);
        System.out.println("預期:   " + expected);
        System.out.println("通過:   " + result.equals(expected));
        System.out.println("------------------------------------------------");
    }
}
