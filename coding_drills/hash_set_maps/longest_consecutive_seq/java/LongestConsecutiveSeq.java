import java.util.Set;
public class LongestConsecutiveSeq {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int i: nums) {
            numSet.add(i);
        }
        int max=0;
        for (int i:nums) {
            if (numSet.contains(i-1)) {
                continue;
            }
            int curr=i;
            int curr_len=1;
            while (numSet.contains(curr+1)) {
                curr=curr+1;
                curr_len++;
            }
            max = Math.max(curr_len,max);
        }
        return max;
    }
}
