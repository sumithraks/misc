#include <iostream>
#include <vector>

using namespace std;



int find_target(vector<int> nums,int target) {
    int left=0;
    int right=nums.size()-1;
    int mid;
    while (left < right) {
        mid=(left+right)/2;
        if (nums[mid]==target) {
            return mid;
        }
        if (nums[left] <= nums[mid]) {
            if (nums[left]<=target && target < nums[mid])
                right = mid-1;
            else
                left = mid+1;
        } else {
            if (nums[mid] < target && target <= nums[right])
                left = mid+1;
            else
                right = mid-1;
        }
    }
    if (nums[left]==target)
        return left;
    else
        return -1;
}

int main(int argc,char **argv) {
    vector<int> nums = {8,9,1,2,3,4,5,6,7};
    cout << find_target(nums,1) << endl;
    cout << find_target(nums,9) << endl;
    cout << find_target(nums,19) << endl;
}