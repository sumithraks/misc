def nextPermutation(nums: list[int]) -> None:
    pivot = len(nums)-2
    while pivot>=0 and nums[pivot]>=nums[pivot+1]:
        pivot-=1
    print("Pivot = ",pivot)
    if pivot<=-1:
        nums.reverse()
        return
    right_most = len(nums)-1
    while (nums[right_most]<=nums[pivot]):
        right_most-=1
    nums[pivot],nums[right_most]=nums[right_most],nums[pivot]
    nums[pivot+1:]=reversed(nums[pivot+1:])


nums=[3,2,1]
nextPermutation(nums)
print(nums)