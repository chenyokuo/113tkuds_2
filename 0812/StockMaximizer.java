import java.util.*;

public class StockMaximizer {

    public static int maxProfit(int[] prices, int k) {
        if (prices == null || prices.length == 0 || k == 0) return 0;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        int n = prices.length;
        int buy = 0;

        while (buy < n - 1) {
            // 找到當前的買入點（局部最小）
            while (buy < n - 1 && prices[buy] >= prices[buy + 1]) {
                buy++;
            }
            int sell = buy + 1;

            // 找到當前的賣出點（局部最大）
            while (sell < n && prices[sell] >= prices[sell - 1]) {
                sell++;
            }

            // 若找到有效的交易，記錄利潤
            if (buy < n && sell - 1 < n && prices[sell - 1] > prices[buy]) {
                int profit = prices[sell - 1] - prices[buy];
                maxHeap.offer(profit);
            }

            // 從下一段開始尋找
            buy = sell;
        }

        int totalProfit = 0;
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            totalProfit += maxHeap.poll();
        }

        return totalProfit;
    }

    // =================== 測試主程式 ===================
    public static void main(String[] args) {
        test(new int[]{2, 4, 1}, 2, 2);
        test(new int[]{3, 2, 6, 5, 0, 3}, 2, 7);
        test(new int[]{1, 2, 3, 4, 5}, 2, 4);
        test(new int[]{5, 4, 3, 2, 1}, 2, 0); // 無利潤
    }

    private static void test(int[] prices, int k, int expected) {
        int result = maxProfit(prices, k);
        System.out.println("價格: " + Arrays.toString(prices));
        System.out.println("K = " + k + "，最大利潤: " + result + "，預期: " + expected);
        System.out.println("通過: " + (result == expected));
        System.out.println("------------------------------------------------");
    }
}
