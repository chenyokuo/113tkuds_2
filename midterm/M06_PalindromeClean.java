import java.io.*;

public class M06_PalindromeClean {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        if (isPalindromeClean(s)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private static boolean isPalindromeClean(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            // 移動左指標直到遇到英文字母
            while (i < j && !Character.isLetter(s.charAt(i))) i++;
            // 移動右指標直到遇到英文字母
            while (i < j && !Character.isLetter(s.charAt(j))) j--;

            if (i < j) {
                char ci = Character.toLowerCase(s.charAt(i));
                char cj = Character.toLowerCase(s.charAt(j));
                if (ci != cj) return false;
                i++;
                j--;
            }
        }
        return true;
    }
}
