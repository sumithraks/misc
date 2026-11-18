from collections import defaultdict

def anagram_finder_substr(pat:str, txt:str)->int:
    if pat == None or len(pat.strip())==None:
        return 0
    if txt==None or len(txt.strip())==0:
        return 0
    if len(pat)>len(txt):
        return 0
    pat_counts = [0]*26;
    for i in pat:
        pat_counts[ord(i)-ord('a')]+=1

    left=right=0
    counts = [0]*26;
    num_anagrams=0
    while right < len(txt):
        counts[ord(txt[right])-ord('a')]+=1
        if right-left+1==len(pat):
            if counts == pat_counts:
                num_anagrams+=1
            counts[ord(txt[left])-ord('a')]-=1
            left+=1
        right+=1
    return num_anagrams

print(anagram_finder_substr("abcd","bacdgabcda"))