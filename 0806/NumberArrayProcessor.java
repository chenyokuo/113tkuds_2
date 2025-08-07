import java.util.*;

public class NumberArrayProcessor {

    public static void main(String[] args) {
        int[] array = {4, 7, 4, 2, 7, 1, 3, 2, 9, 5};
        int[] sortedA = {1, 3, 5, 7, 9};
        int[] sortedB = {2, 4, 6, 8};

        System.out.println("=== 原始陣列 ===");
        printArray(array);

        System.out.println("\n=== 移除重複元素後 ===");
        int[] unique = removeDuplicates(array);
        printArray(unique);

        System.out.println("\n=== 合併兩個已排序陣列 ===");
        int[] merged = mergeSortedArrays(sortedA, sortedB);
        printArray(merged);

        System.out.println("\n=== 陣列中出現最頻繁的元素 ===");
        int mostFrequent = findMostFrequentElement(array);
        System.out.println("最常出現的元素為：" + mostFrequent);

        System.out.println("\n=== 分割為兩個總和近似相等的子陣列 ===");
        splitArray(array);
    }

    // 移除重複元素
    public static int[] removeDuplicates(int[] array) {
        Set<Integer> set = new LinkedHashSet<>();
        for (int n : array) set.add(n);
        return set.stream().mapToInt(Integer::intValue).toArray();
    }

    // 合併兩個已排序陣列
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            result[k++] = a[i] < b[j] ? a[i++] : b[j++];
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    // 找出出現頻率最高的元素
    public static int findMostFrequentElement(int[] array) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int n : array) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }
        int maxFreq = 0, result = array[0];
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }

    // 將陣列分割成兩個總和近似相等的子陣列（簡易貪心法）
    public static void splitArray(int[] array) {
        Arrays.sort(array);
        List<Integer> group1 = new ArrayList<>();
        List<Integer> group2 = new ArrayList<>();
        int sum1 = 0, sum2 = 0;

        for (int i = array.length - 1; i >= 0; i--) {
            if (sum1 <= sum2) {
                group1.add(array[i]);
                sum1 += array[i];
            } else {
                group2.add(array[i]);
                sum2 += array[i];
            }
        }

        System.out.println("子陣列1：" + group1 + "，總和：" + sum1);
        System.out.println("子陣列2：" + group2 + "，總和：" + sum2);
    }

    // 陣列列印
    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }
}
