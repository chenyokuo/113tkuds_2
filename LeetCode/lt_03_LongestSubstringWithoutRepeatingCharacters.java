
import java.util.HashMap;

class Solution {

    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> last = new HashMap<>();
        int left = 0, ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (last.containsKey(c) && last.get(c) >= left) {
                left = last.get(c) + 1;
            }
            last.put(c, i);
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
