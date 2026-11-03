def max_container(height:list)->int:
    area=0
    left=0
    right=len(height)-1;
    while (left<right):
        curr = min(height[left],height[right])*(right-left)
        area = max(curr,area)
        if height[left]<height[right]:
            left+=1
        else:
            right-=1
    return area

print(max_container([1,8,6,2,5,4,8,3,7]))