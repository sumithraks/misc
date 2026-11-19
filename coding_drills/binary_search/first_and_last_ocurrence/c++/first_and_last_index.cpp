#include <iostream>
#include <vector>

using namespace std;

int search_lower_bound(vector<int>& nums, int target) {

    int left=0;
    int right=nums.size()-1;
    int mid;
    while (left < right) {
        mid = (left+right)/2;
        if (nums[mid]>target) {
            right=mid-1;
        }
        else if (nums[mid]<target) {
            left=mid+1;
        } else {
            right=mid;
        }
    }
    if (nums[left]==target)
    return left;
    else return -1;
}

int search_upper_bound(vector<int>& nums, int target) {
    int left=0;
    int right=nums.size()-1;
    int mid;
    while (left < right) {
        mid = (left+right)/2+1;
        if (nums[mid]>target) {
            right=mid-1;
        }
        else if (nums[mid]<target) {
            left=mid+1;
        } else {
            left=mid;
        }
    }
    if (nums[right]==target)
    return right;
    else return -1;
}

vector<int> searchRange(vector<int>& nums, int target) {
    vector<int> result;
    if (nums.empty()) {
            result.push_back(-1);
            result.push_back(-1);
            return result;    
    }
    int lower = search_lower_bound(nums,target);
    int upper = search_upper_bound(nums,target);
    result.push_back(lower);
    result.push_back(upper);
    return result;
}

void display(vector<int> res) {
    for (int a:res)
        cout << a << endl;
}

int main(int argc,char** argv) {
    vector<int> nums = {5,7,7,8,8,10};
    display(searchRange(nums, 8));
    cout << "_______" << endl;
    display(searchRange(nums, 9));
    return 0;
}