// 題目：緊急通報格式括號檢查
// 給定一段僅含 ()[]{} 的字串，請判斷括號是否完全正確巢狀，且無錯配。
// 若合法回傳 true，否則 false。

import java.io.*;
import java.util.*;

public class LC20_ValidParentheses_AlertFormat {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) {
            s = "";
        }

        System.out.println(isValid(s));
    }

    // 核心檢查函式
    private static boolean isValid(String s) {
        // Map 建立閉→開對應
        Map<Character, Character> match = new HashMap<>();
        match.put(')', '(');
        match.put(']', '[');
        match.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                // 遇到開括號 → push
                stack.push(c);
            } else {
                // 遇到閉括號 → 檢查棧頂
                if (stack.isEmpty()) {
                    return false;
                }
                if (stack.peek() != match.get(c)) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}
/*
解題思路：
1. 使用 Stack 模擬括號巢狀：
   - 遇開括號 '('、'['、'{' → push。
   - 遇閉括號 → 檢查棧頂是否對應，否則立即返回 false。
2. 全部掃描完畢後，若 stack 為空 → true；否則 false。
3. 特殊案例：
   - 空字串 → true
   - 以閉括號開頭 → false
   - 巢狀/並列混合仍正確 → true
時間複雜度 O(n)，空間 O(n)。
 */
