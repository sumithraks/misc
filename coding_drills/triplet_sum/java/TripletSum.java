import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class TripletSum {

    private static  List<Integer> pairSum(List<Integer> nums,int startIndex,int target) {
        List<Integer> result = new ArrayList<>();
        if (nums.size()-startIndex<2) {
            return result;
        }
        int left=startIndex, right = nums.size()-1;
        long sum=0;
        while (left<right) {
            sum=nums.get(left)+nums.get(right);
            if (sum == target){
                result.add(nums.get(left));
                result.add(nums.get(right));
                return result;
            } 
            if (sum < target) 
                left++;
            else
                right--;
        }
        return result;
    }
    public static List<Integer> tripletSum(List<Integer> nums) {
        
        List<Integer> result = new ArrayList<>();
        if (nums.isEmpty()||nums.size()<3)
            return result;
        Collections.sort(nums);
        for (int i=0; i < nums.size()-1;++i) {
            if (nums.get(i) > 0) {
                break;
            }
            if (i>0 && nums.get(i)==nums.get(i-1))
                continue;
            int target = -1 * nums.get(i);
            List<Integer> pair = pairSum(nums,i+1,target);
            if (!pair.isEmpty()) {
                result.add(nums.get(i));
                result.addAll(pair);
                return result;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        List<Integer> result = tripletSum(nums);
        if (result.size()==0) {
            System.out.println("Passed for empty array");
        }
        nums.add(0);
        result = tripletSum(nums);
        if (result.size()==0) {
            System.out.println("Passed for single element array");
        }
        nums.clear();
        nums.add(1);
        nums.add(-1);
        result = tripletSum(nums);
        if (result.size()==0) {
            System.out.println("Passed for two element  array");
        }
        nums.clear();
        nums.add(0);
        nums.add(0);
        nums.add(0);
        result = tripletSum(nums);
        List<Integer> expected=List.of(0,0,0);
        if (result.size()==3 && expected.equals(result) ) {
            System.out.println("Passed for all zero array");
        } else {
            System.out.println("Not passed "+result);
        }

        nums.clear();
        nums.add(1);
        nums.add(0);
        nums.add(1);
        result = tripletSum(nums);
        if (result.size()==0) {
            System.out.println("Passed for all positives");
        }

        nums.clear();
        nums.add(0);
        nums.add(0);
        nums.add(1);
        nums.add(-1);
        nums.add(1);
        nums.add(-1);
        result = tripletSum(nums);
        List<Integer> expected1=List.of(-1,0,1);
        if (result.size()==3 && expected1.equals(result) ) {
            System.out.println("Passed for all duplicates to zero");
        } else {
            System.out.println("Not passed "+result);
        }
    }
    
}
