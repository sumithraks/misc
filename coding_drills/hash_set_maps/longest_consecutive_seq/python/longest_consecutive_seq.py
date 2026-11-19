def longestConsecutive(nums: list[int]) -> int:
    nums_present = set()
    for i in nums:
        nums_present.add(i)

    max_len=0
    for i in nums_present:
        if i-1 in nums_present:
            continue
        else:
            curr_num=i
            curr_length=1
            while (curr_num+1) in nums_present:
                curr_length+=1
                curr_num+=1
            max_len=max(curr_length,max_len)
    return max_len

print(longestConsecutive([100,4,200,1,3,2]))
print(longestConsecutive([0,3,7,2,5,8,4,6,0,1]))