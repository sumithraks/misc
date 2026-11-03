import java.util.List;
import java.util.ArrayList;

public class Container {

    public static int maxVolume(List<Integer> heights) {
        int left=0;
        int right=heights.size()-1;
        int area = 0;
        while (left < right) {
            int curr_area  = Math.min(heights.get(right),heights.get(left))*(right-left);
            System.out.println("Left: "+left+" Right: "+right+" area: "+curr_area);
            area = Math.max(area,curr_area);
            if (heights.get(left)<heights.get(right)) {
                left++;
            } else {
                right--;
            }
        }
        return area;
    }

    public static void main(String[] args) {
        List<Integer> test1=List.of(1,8,6,2,5,4,8,3,7);
        System.out.println(maxVolume(test1));
    }
}