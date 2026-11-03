#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void next_lexicographic(vector<int> &nums) 
{
    int pivot=nums.size()-2;
    while (pivot >=0 && nums[pivot]>=nums[pivot+1])
        pivot-=1;
    if (pivot==-1)
        std::reverse(nums.begin(),nums.end());
        return;
    int right_most=nums.size()-1;
    while (nums[right_most]<=nums[pivot]) {
        right_most-=1;
    }
    int a = nums[pivot];
    nums[pivot]=nums[right_most];
    nums[right_most]=a;
    vector<int>::iterator it=nums.begin();
    
    for (int i=0; i <=pivot; i++)
    {
        it++;
    }
    std::reverse(it,nums.end());
}

int main(int argc,char** argv) {
    vector<int> nums={1, 2, 3};
    next_lexicographic(nums);
    for (vector<int>::iterator it = nums.begin();it!=nums.end();++it)
    {
        cout << *it << " ";
    }
}