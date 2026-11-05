def twoSum(nums: List[int], target: int) -> List[int]:
    index = dict()
    for i in range(len(nums)):
        index[nums[i]]=i
    for i in range(len(nums)):
        other = target-nums[i]
        if other in index:
            return [i,index[other]]

print(twoSum([-1,4,3,1],3))
