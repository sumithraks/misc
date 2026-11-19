import java.util.Arrays;

public class Runner {
    public static int minCuttingWork(int[] heights, int k) {
        int left=0;
        int right = Arrays.stream(heights).max().orElse(0);
        while (left < right) {
            int mid = (left+right)/2+1;
            if (cutsEnoughWood(mid,k,heights)) {
                left=mid;
            }
            else {
                right=mid-1;
            }
        }
        return right;
    }

    private static boolean cutsEnoughWood(int mid,int k,int[] heights) {
        int collected = 0;
        for (int i:heights) {
            if (i>=mid) {
                collected+=(i-mid);
            }
        }
        return collected >= k;
    }

    public static void main() {
        int[] heights = {2,6,3,8};
        int k = 7;
        System.out.println("Min "+minCuttingWork(heights,k));
    }
}
