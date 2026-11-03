def moveZeroes(nums: List[int]) -> None:
    """
    Do not return anything, modify nums in-place instead.
    """
    right=left=0
    while (right < len(nums)):
        if nums[right]!=0:
            nums[left],nums[right]=nums[right],nums[left]
            left+=1
        right+=1

nums=[0,1,0,3,12]
moveZeroes(nums);
print(nums);
