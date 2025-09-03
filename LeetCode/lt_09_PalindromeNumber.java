
class Solution {

    public boolean isPalindrome(int x) {
        // 負數或尾數是 0（但不是 0 本身）
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }

        // 偶數長度: x == rev
        // 奇數長度: x == rev/10
        return x == rev || x == rev / 10;
    }
}
