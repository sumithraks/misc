#include <iostream>
#include <vector>
#include <algorithm> 

using namespace std;

vector<int> pair_sum(const vector<int> &nums, const int position, const int target)
{
    vector<int> result;
    if (nums.size()-position<2) {
        return result;
    }
    int left=position, right=nums.size()-1;
    long sum=0;
    while (left<right) {
        sum=nums[left]+nums[right];
        if (sum==target)
        {
            result.push_back(nums[left]);
            result.push_back(nums[right]);
            return result;
        } 
        if (sum<target)
            left++;
        else
            right--;
    }
    return result;

}

vector<int> tripletSum(vector<int> nums)
{
    vector<int> result;
    if (nums.empty() || nums.size()<3) {
        return result;
    }
    std::sort(nums.begin(),nums.end());
    for (int i=0;i<nums.size()-1;++i) {
        if (nums[i]>0)
            break;
        if (i>0&&nums[i]==nums[i-1])
            continue;
        int target = -1*nums[i];
        vector<int> pair = pair_sum(nums,i+1,target);
        if (pair.empty())
            continue;
        else {
            result.push_back(nums[i]);
            result.insert(result.end(),pair.begin(),pair.end());
            return result;
        }
    }
    return result;
}

int main(int argc, char**argv) {
    vector<int> nums={0,0,1,-1,0,1};
    vector<int> result = tripletSum(nums);
    for (vector<int>::iterator it = result.begin(); it != result.end(); it++) {
        cout << *it << " ";
    }
}
