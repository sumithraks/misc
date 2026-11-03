def pair_sum(nums:list,index:int,target:int)->list:
    result = []
    if len(nums)-index < 2:
        return result
    left = index
    right=len(nums)-1
    sum=0
    while (left<right):
        sum=nums[left]+nums[right]
        if sum==target:
            result.append(nums[left])
            result.append(nums[right])
            return result
        if sum < target:
            left+=1
        else:
            right-=1
    return result

def triplet_sum(nums:list)->list:
    if len(nums) == 0 or len(nums)<3:
            return []
    result = []
    nums=sorted(nums)
    for i in range(len(nums)):
        if nums[i] > 0:
            break;
        if i>0 and nums[i]==nums[i-1]:
            continue;
        target = -1 * nums[i]
        pairs = pair_sum(nums,i+1,target);
        if len(pairs) != 0:
            result.append(nums[i])
            result.extend(pairs)
            return result
    return result

print(triplet_sum([]))
print(triplet_sum([0]))
print(triplet_sum([1,-1]))
print(triplet_sum([1,-1,0]))
print(triplet_sum([0,0,0]))
print(triplet_sum([0,1,1,0,-1]))

    
