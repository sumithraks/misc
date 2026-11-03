import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class LexicographicSeq {

    public static void next_seq(List<Integer> nums){
        int pivot=nums.size()-2;
        while (pivot >=0 && nums.get(pivot)>=nums.get(pivot+1))
            pivot--;
        if (pivot==-1)
            Collections.reverse(nums);
            return;
        int right_most=nums.size()-1;
        while (nums.get(right_most)<=nums.get(pivot)) {
            right_most--;
        }
        int a = nums.get(pivot);
        nums.set(pivot,nums.get(right_most));
        nums.set(right_most,a);
        Collections.reverse(nums.subList(pivot+1,nums.size()-1));
    }

    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.add(3);
        next_seq(nums);
        System.out.println(nums);
    }
}
