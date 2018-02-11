/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EjercicioEnLinea;

/**
 *
 * @author Alejandro Arroyave, Luis Javier 
 */
public class EjerciciosCodingBat {

    //Recursión 2: GroupSum6
    public boolean groupSum6(int start, int[] nums, int target) {
        if (start >= nums.length) {
            return target == 0;
        } else {
            if (nums[start] == 6) {
                return groupSum6(start + 1, nums, target - nums[start]);
            } else {
                return groupSum6(start + 1, nums, target - nums[start])
                        || groupSum6(start + 1, nums, target);
            }
        }
    }
    
    //Recursión 2: GroupNoAdj
    public boolean groupNoAdj(int start, int[] nums, int target) {
        if (start >= nums.length) {
            return target == 0;
        } else {
            return groupNoAdj(start + 2, nums, target - nums[start])
                    || groupNoAdj(start + 1, nums, target);
        }
    }

    //Recursión 2: GroupSum5
    public boolean groupSum5(int start, int[] nums, int target) {
        if (start >= nums.length) {
            return target == 0;
        } else {
            if (nums[start] % 5 == 0) {
                return groupSum5(start + 1, nums, target - nums[start]);
            }
            if (start != 0 && nums[start] == 1 && nums[start - 1] % 5 == 0) {
                return groupSum5(start + 1, nums, target);
            }
        }
        return groupSum5(start + 1, nums, target - nums[start])
                || groupSum5(start + 1, nums, target);
    }

    //Recursión 2: GroupSumClump
    public boolean groupSumClump(int start, int[] nums, int target) {
        if (start >= nums.length) {
            return target == 0;
        } else {
            int n = 1;
            while (start <= nums.length - 2) {
                if (nums[start] != nums[start + 1]) {
                    break;
                }
                start++;
                n++;
            }
            n = n * nums[start];
            return groupSumClump(start + 1, nums, target - n)
                    || groupSumClump(start + 1, nums, target);
        }

    }

    //Recursión 2: SplitArray
    public boolean splitArray(int[] nums) {
        return splitArrayAux(nums, 0, 0, 0);
    }

    public boolean splitArrayAux(int[] nums, int start, int g1, int g2) {
        if (start >= nums.length) {
            return g1 == g2;
        } else {
            return splitArrayAux(nums, start + 1, g1 + nums[start], g2)
                    || splitArrayAux(nums, start + 1, g1, g2 + nums[start]);
        }
    }

}
