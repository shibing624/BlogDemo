package xm.math.leetcode;

import org.junit.Test;

/**
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that
 * adjacent houses have security system connected and it will automatically contact the police if two adjacent houses
 * were broken into on the same night.
 * <p>
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
 * of money you can rob tonight without alerting the police.
 * <p>
 * input:1,2,3,12,15,2,8
 * output:27
 *
 * @author XuMing
 */
public class HouseRobber {
    public int rob(int[] nums) {
        int pre = 0;
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = Math.max(pre + nums[i], cur);
            pre = cur;
            cur = temp;
        }
        return cur;
    }


    @Test
    public void test() {
        HouseRobber demo = new HouseRobber();
        int[] nums = new int[]{
                1, 2, 3, 12, 15, 2, 8
        };
        int[] nums2 = new int[]{
                2, 1, 1, 2
        };
        System.out.println(demo.rob(nums));
        System.out.println(demo.rob(nums2));
    }

}
