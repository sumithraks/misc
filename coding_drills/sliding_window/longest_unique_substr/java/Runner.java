import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class Runner {
    
    public static int getLongestUniqSubstring(String txt) {
        Map<Character,Integer> indexes = new HashMap<>();
        int currMax=0;
        int left=0; 
        int right=0;
        while (right<txt.length()) {
            if (indexes.containsKey(txt.charAt(right)) && indexes.get(txt.charAt(right)) >=left ) 
                left = indexes.get(txt.charAt(right));
            indexes.put(txt.charAt(right),right);
            currMax = Math.max(currMax,right-left);
            right++;
        }
        return currMax;
    }

    public static void main(String [] args) {
        System.out.println( getLongestUniqSubstring("abcabcbb"));
        System.out.println( getLongestUniqSubstring("bbbbb"));
        System.out.println( getLongestUniqSubstring("pwwkew") );
    }
}
