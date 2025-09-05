// 題目：北捷進出站最長有效片段
// 給定一行只含 '(' 與 ')' 的字串，請回傳最長「括號合法」子字串的長度（連續片段）。

import java.io.*;
import java.util.*;

public class LC32_LongestValidParen_Metro {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) {
            s = "";
        }
        System.out.println(longestValidParentheses(s));
    }

    // 索引棧解法：棧底放 -1 作為基準
    private static int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);                 // 進站事件 → 推入索引
            } else { // c == ')'
                stack.pop();                   // 嘗試匹配最近的 '('
                if (stack.isEmpty()) {
                    stack.push(i);             // 無可配對者，重置基準
                } else {
                    ans = Math.max(ans, i - stack.peek()); // 合法長度 = 當前索引 - 棧頂基準
                }
            }
        }
        return ans;
    }
}
/*
解題思路：
1. 使用「索引棧」維護合法區段基準：初始 push(-1)。
2. 掃描字串：
   - 遇 '(' → push(索引)。
   - 遇 ')' → pop 一個；若棧空 → push(當前索引) 作為新基準；
               否則更新答案 ans = max(ans, i - stack.peek())。
3. 這可正確處理孤立的 ')' 與斷點，避免越界。
時間複雜度 O(n)，空間 O(n)（最壞情況全為 '('）。
 */
