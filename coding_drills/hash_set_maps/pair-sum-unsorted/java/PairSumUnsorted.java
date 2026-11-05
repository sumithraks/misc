import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class PairSumUnsorted {

    public static int[] pair_sum_unsorted(int[] nums, int target) {
        HashMap<Integer,Integer> indices = new HashMap<>();
        int[] result = new int[2];
        for (int i=0;i<nums.length;++i) {
            indices.put(nums[i],i);
        }
        for (int i=0;i<nums.length;++i) {
            int other = target-nums[i];
            if (indices.containsKey(other)) {
                result[0]=i;
                result[1]=indices.get(other);
                return result;
            }
        }
        return result;
    }

    public static void main(String args[]) {
        int[] nums = {-1,3,4,2};
        int [] result = pair_sum_unsorted(nums,11);
        System.out.println(result[0]+","+result[1]);
        result = pair_sum_unsorted(nums,3);
        System.out.println(result[0]+","+result[1]);
    }
}