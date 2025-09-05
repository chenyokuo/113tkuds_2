// 檔名：LC01_TwoSum_THSRHoliday.java
// 題目：高鐵連假加班車 Two Sum
// 說明：給定各班次尚可釋出的座位數陣列與 target（臨時新增需求總座位），
//      找出兩個不同班次 i, j，使 seats[i] + seats[j] == target；若無則輸出 -1 -1。
// 輸入：
//   第一行：n target
//   第二行：n 個整數（可為負、零、正；範圍至 ±1e9）
// 輸出：
//   兩個索引（任意一組解即可），或 -1 -1
//
// 解法觀念：一次掃描 + HashMap
//   - 掃到座位數 x 時，若 map 內已存在等待的 x（代表先前某班次缺 x 才能湊到 target），則配對成功。
//   - 否則把「還需要的數 need = target - x」記到 map，對應目前索引。
// 複雜度：時間 O(n)，空間 O(n)

import java.io.*;
import java.util.*;

public class LC01_TwoSum_THSRHoliday {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 讀第一行：n 與 target
        st = new StringTokenizer(safeReadLine(br));
        int n = Integer.parseInt(st.nextToken());
        long target = Long.parseLong(st.nextToken());

        // 讀第二行：n 個座位數
        long[] seats = new long[n];
        st = new StringTokenizer(safeReadLine(br));
        for (int i = 0; i < n; i++) {
            if (!st.hasMoreTokens()) {
                // 若座位數分多行輸入，持續補讀
                st = new StringTokenizer(safeReadLine(br));
            }
            seats[i] = Long.parseLong(st.nextToken());
        }

        // HashMap<等待的數值 need, 索引 index>
        Map<Long, Integer> needToIndex = new HashMap<>(Math.max(16, n * 2));

        for (int i = 0; i < n; i++) {
            long x = seats[i];

            // 若已有人在等 x，則完成配對
            Integer j = needToIndex.get(x);
            if (j != null) {
                System.out.println(j + " " + i);
                return;
            }

            // 否則登記「還需要的數」
            long need = target - x;
            // 僅在未登記時放入；若已有值，保留先到者（任一解即可）
            needToIndex.putIfAbsent(need, i);
        }

        // 若完全無解
        System.out.println("-1 -1");
    }

    // 安全讀行：跳過可能的空行
    private static String safeReadLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null && line.trim().isEmpty()) {
            // 跳過空白行
        }
        return line == null ? "" : line;
    }
}
