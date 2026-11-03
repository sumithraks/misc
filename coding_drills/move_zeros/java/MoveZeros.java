import java.util.List;
import java.util.ArrayList;

public class MoveZeros {
    public static void move(List<Integer> nums) {
        int left=0;
        for (int right=0;right < nums.size();++right) {
            if (nums.get(right)!=0) {
                int a = nums.get(left);
                nums.set(left,nums.get(right));
                nums.set(right,a);
                left++;
            }
        }
    }

    public static void main(String [] args) {
        List<Integer> nums=new ArrayList<>();
        nums.add(0);
        nums.add(1);
        nums.add(0);
        nums.add(3);
        nums.add(12);

        move(nums);
        System.out.println(nums);
    }
}
