public class Runner {
    
    public static int findInsertionIndex(int[] nums, int target) {
        int left=0;
        int right = nums.length-1;
        int mid;
        while (left <= right) {
            mid = (left+right)/2;
            if (nums[mid]==target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
     
        int nums[]={1,3,5,6};
        System.out.println(findInsertionIndex(nums,2));
        int nums2[]={1,3,5,6};
        System.out.println(findInsertionIndex(nums2,5));
        int nums3[]= {1,3,5,6};
        System.out.println(findInsertionIndex(nums3,7));
    }
}
