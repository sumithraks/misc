from collections import defaultdict

def characterReplacement(s: str, k: int) -> int:
        if len(s)==0:
            return 0
        freq=defaultdict(int)
        highest_freq=0
        currMax = 0
        right=0
        left=0
        while (right < len(s)):
            freq[s[right]]+=1
            highest_freq=max(highest_freq,freq[s[right]])
            num_chars_to_replace = right-left+1-highest_freq
            if num_chars_to_replace > k:
                freq[s[left]]-=1
                left+=1
            currMax=max(right-left+1,currMax);
            right+=1
        return currMax

print(characterReplacement("ABAB",2))