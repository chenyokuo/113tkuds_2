
class Solution {

    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        int rows = Math.min(numRows, s.length());
        StringBuilder[] sb = new StringBuilder[rows];
        for (int i = 0; i < rows; i++) {
            sb[i] = new StringBuilder();
        }

        int cur = 0;
        boolean down = true; // true: 往下；false: 往上
        for (char c : s.toCharArray()) {
            sb[cur].append(c);
            if (cur == 0) {
                down = true; 
            }else if (cur == rows - 1) {
                down = false;
            }
            cur += down ? 1 : -1;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder row : sb) {
            ans.append(row);
        }
        return ans.toString();
    }
}
