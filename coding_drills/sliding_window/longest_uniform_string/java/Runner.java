import java.util.Map;
import java.util.HashMap;

public class Runner {
    
    public static int characterReplacement(String s, int k) {
        Map<Character,Integer> freq = new HashMap<>();
        int right=0;int left=0;
        int highest_freq = 0;
        int max_window = 0;
        int num_chars_to_replace =0;
        while (right < s.length()) {
            freq.put(s.charAt(right),freq.getOrDefault(s.charAt(right),0)+1);
            highest_freq=Math.max(highest_freq,freq.get(s.charAt(right)));
            num_chars_to_replace = right-left+1-highest_freq;
            if (num_chars_to_replace>k) {
                freq.put(s.charAt(left),freq.get(s.charAt(left)-1));
                left++;
            }
            max_window=right-left+1;
            right++;
        }
        return max_window;
    }

    public static void main(String[] args) {
        System.out.println(characterReplacement("ABAB",2));
    }
}
