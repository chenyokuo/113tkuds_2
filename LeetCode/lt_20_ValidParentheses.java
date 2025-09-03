// 題目：Valid Parentheses
// 給定一個只包含 '(', ')', '{', '}', '[', ']' 的字串 s，判斷字串是否有效。
// 有效條件：
// 1. 左括號必須由相同型別的右括號關閉。
// 2. 左括號必須以正確順序關閉。
// 3. 每個右括號都必須對應一個有效的左括號。

import java.util.*;

class Solution {

    public boolean isValid(String s) {
        // 使用 Stack 來存放未匹配的左括號
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            // 如果是左括號，壓入堆疊
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                // 如果是右括號，堆疊必須有對應的左括號
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                // 檢查括號是否匹配
                if (c == ')' && top != '(') {
                    return false;
                }
                if (c == '}' && top != '{') {
                    return false;
                }
                if (c == ']' && top != '[') {
                    return false;
                }
            }
        }
        // 最後堆疊必須為空，才是有效字串
        return stack.isEmpty();
    }
}

/*
解題思路：
1. 利用 Stack 資料結構模擬括號匹配：
   - 遇到左括號，壓入堆疊。
   - 遇到右括號，檢查堆疊頂端是否為對應左括號，不符則回傳 false。
2. 遍歷結束後，若堆疊為空，表示所有括號都正確匹配，回傳 true。
3. 時間複雜度 O(n)，每個字元只進出堆疊一次。
4. 空間複雜度 O(n)，最壞情況所有字元都是左括號。
 */
