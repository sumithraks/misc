import java.util.List;
import java.util.ArrayList;


public class PairSum {


    public static boolean  check(TestCase tc) {
        List<Integer> result = pairSum(tc.nums(), tc.target());
        if (!tc.expectedToFind()) 
            return result.isEmpty();
        if (result.size() != 2) return false;
        int i = result.get(0),j = result.get(1);
        return tc.nums().get(i) + tc.nums().get(j) == tc.target();
    }

    public static List<Integer> pairSum(List<Integer> nums,int target) {
        if (nums.size()<2)
            return new ArrayList<Integer>();
        int left=0;
        int right=nums.size()-1;
        long sum;
        List<Integer> result = new ArrayList<>();
        while (left<right) {
            sum=nums.get(left)+nums.get(right);
            if (sum==target) {
                result.add(left);
                result.add(right);
                return result;
            }
            if (sum<target)
                left++;
            else
                right--;
        }
        return new ArrayList<Integer>();
    }

    public static void main(String[] args) {
        List<TestCase> tests = List.of(
        new TestCase("basic pair",     List.of(-5, -2, 3, 4, 6), 7,  true),
        new TestCase("negatives only", List.of(-8, -3, -1),      -4, true),
        new TestCase("no pair exists", List.of(1, 2, 3),         10, false),
        new TestCase("empty input",    List.of(),                0,  false),
        new TestCase("single element", List.of(5),               5,  false),
        new TestCase("duplicates",     List.of(1, 1, 4),         2,  true)
    );
         int passed = 0;
         for (TestCase t:tests) {
            boolean ok = check(t);
            System.out.println((ok ? "PASS " : "FAIL ") +" "+t.name() );
            if (ok) ++passed;
         }
         System.out.println(passed+"/"+tests.size()+" passed");
    }
}