public class Runner {
 
    public static int findInRotatedArray(int[] nums, int target) {
        int left=0;
        int right=nums.length-1;
        int mid;
        while (left < right) {
            mid=left+right/2;
            if (nums[mid]==target)
                return mid;
            if (nums[left] < nums[mid]) {
                if (nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
            else {
                if (nums[mid] < target && target <= nums[right])
                    left = mid+1;
                else
                    right = mid-1;
            }

        }
        if (nums[left]==target)
            return left;
        else
            return -1;
    }

    public static void main(String[] args) {
        int []nums = {8,9,1,2,3,4,5,6,7};
        System.out.println(findInRotatedArray(nums,1));
    }
}
