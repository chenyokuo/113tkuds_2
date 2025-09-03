
class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int left = 0, right = m;
        int half = (m + n + 1) / 2;

        while (left <= right) {
            int i = (left + right) >>> 1;
            int j = half - i;

            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                if (((m + n) & 1) == 1) {
                    return Math.max(left1, left2);
                } else {
                    int lmax = Math.max(left1, left2);
                    int rmin = Math.min(right1, right2);
                    return (lmax + rmin) / 2.0;
                }
            } else if (left1 > right2) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }
        return 0.0;
    }
}
