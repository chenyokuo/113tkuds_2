import java.util.*;

public class AdvancedStringRecursion {

    // 1️⃣ 遞迴產生所有排列組合
    public static void generatePermutations(String str) {
        permuteHelper("", str);
    }

    private static void permuteHelper(String prefix, String remaining) {
        if (remaining.isEmpty()) {
            System.out.println(prefix);
            return;
        }
        for (int i = 0; i < remaining.length(); i++) {
            permuteHelper(prefix + remaining.charAt(i),
                          remaining.substring(0, i) + remaining.substring(i + 1));
        }
    }

    // 2️⃣ 遞迴實作字串匹配（判斷 pattern 是否為 text 的子字串）
    public static boolean isMatch(String text, String pattern) {
        if (pattern.isEmpty()) return true;
        if (text.isEmpty()) return false;

        if (text.charAt(0) == pattern.charAt(0)) {
            return isMatch(text.substring(1), pattern.substring(1));
        } else {
            return isMatch(text.substring(1), pattern);
        }
    }

    // 3️⃣ 遞迴移除重複字符（保留第一次出現）
    public static String removeDuplicates(String str) {
        return removeDuplicatesHelper(str, new HashSet<>());
    }

    private static String removeDuplicatesHelper(String str, Set<Character> seen) {
        if (str.isEmpty()) return "";

        char ch = str.charAt(0);
        if (seen.contains(ch)) {
            return removeDuplicatesHelper(str.substring(1), seen);
        } else {
            seen.add(ch);
            return ch + removeDuplicatesHelper(str.substring(1), seen);
        }
    }

    // 4️⃣ 遞迴列出所有子字串組合
    public static void generateAllSubstrings(String str) {
        substringHelper(str, 0, "");
    }

    private static void substringHelper(String str, int index, String current) {
        if (index == str.length()) {
            if (!current.isEmpty()) {
                System.out.println(current);
            }
            return;
        }

        // 選擇 index 字元
        substringHelper(str, index + 1, current + str.charAt(index));
        // 不選 index 字元
        substringHelper(str, index + 1, current);
    }

    // 主程式
    public static void main(String[] args) {
        String testStr = "abc";
        String text = "abcdefghijklmnop";
        String pattern = "aceg";

        System.out.println("=== 1️⃣ 所有排列組合 ===");
        generatePermutations(testStr);

        System.out.println("\n=== 2️⃣ 字串匹配 ===");
        System.out.println("pattern 是否存在於 text 中？" + isMatch(text, pattern));  // true

        System.out.println("\n=== 3️⃣ 移除重複字元 ===");
        System.out.println("輸入：aabcbdea → 輸出：" + removeDuplicates("aabcbdea"));

        System.out.println("\n=== 4️⃣ 所有子字串組合 ===");
        generateAllSubstrings("abc");
    }
}
