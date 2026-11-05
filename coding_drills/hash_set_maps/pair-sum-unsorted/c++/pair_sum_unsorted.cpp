#include <iostream>
#include <vector>
#include <map>

using namespace std;

vector<int> pair_sum_unsorted(const vector<int>& nums,int target) {
    map<int,int> location;
    vector<int> result;
    for (int i=0; i < nums.size();++i) {
        location[nums[i]]=i;
    }
    for (int i=0; i < nums.size();++i) {
        int other = target - nums[i];
        if (location.find(other) != location.end() && location[other]!=i) {
            result.push_back(i);
            result.push_back(location[other]);
            return result;
        }
    }
    return result;
}

int main(int argc,char **argv) {
    vector<int> nums = {-1,3,4,2};
    vector<int> result = pair_sum_unsorted(nums,3);
    for (int i=0;i<result.size();++i) {
        cout << result[i] << " " ;
    }
}