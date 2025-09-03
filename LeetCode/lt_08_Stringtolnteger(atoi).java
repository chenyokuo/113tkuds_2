
class Solution {

    public int myAtoi(String s) {
        int n = s.length();
        int i = 0;

        // 1) skip leading spaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // empty after spaces
        if (i == n) {
            return 0;
        }

        // 2) sign
        int sign = 1;
        char ch = s.charAt(i);
        if (ch == '+' || ch == '-') {
            sign = (ch == '-') ? -1 : 1;
            i++;
        }

        // 3) read digits with overflow check
        int res = 0;
        final int LIMIT = 214748364; // Integer.MAX_VALUE / 10
        while (i < n) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                break;
            }
            int d = c - '0';

            // 4) overflow clamp (before res = res*10 + d)
            if (res > LIMIT || (res == LIMIT && d > (sign == 1 ? 7 : 8))) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }

            res = res * 10 + d;
            i++;
        }

        return sign * res;
    }
}
