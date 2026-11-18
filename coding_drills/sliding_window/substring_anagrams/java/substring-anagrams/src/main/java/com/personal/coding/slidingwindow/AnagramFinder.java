package com.personal.coding.slidingwindow;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnagramFinder {

    public static int search(String pat, String txt) {
        
        if (pat == null || pat.isBlank())
            return 0;
        if (txt==null||txt.isBlank())
            return 0;
        int pattern_cnts[] = new int[26];
        if (pat.length()>txt.length()) {
            return 0;
        }
        int num_anagrams=0;

        for (int i=0; i < pat.length();++i) {
            char c = pat.charAt(i);
            int index = ((int)c-(int)'a');
            pattern_cnts[index]++;
        }


        int left=0;int right=0;
        int counts[] = new int[26];
        while (right < txt.length()) {
            char c = txt.charAt(right);
            int index = ((int)c-(int)'a');
            counts[index]++;
            if (right-left+1 == pat.length())
            {
                if (Arrays.equals(counts, pattern_cnts))
                    num_anagrams++;
                counts[(int)txt.charAt(left)-(int)'a']--;
                left++;
            }
            right++;
        }
        return num_anagrams;
    }
    
}
