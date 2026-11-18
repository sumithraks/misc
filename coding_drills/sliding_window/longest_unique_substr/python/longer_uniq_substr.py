def lengthOfLongestSubstring( s: str) -> int:
    index = dict()
    maxlen = 0
    right = 0
    left =0
    while (right < len(s)):
        if (s[right] in index and index[s[right]] >= left):
            left=index[s[right]]+1
        maxlen = max(maxlen,right-left+1)
        index[s[right]]=right
        right+=1
    return maxlen

print(lengthOfLongestSubstring("s"))
print(lengthOfLongestSubstring("abcabcbb"))