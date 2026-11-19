#include <set>
#include <iostream>
#include <vector>

using namespace std;
int longestConsecutive(vector<int>& nums) {
    set<int> numSet;
    for (int i:nums) {
        numSet.insert(i);
    }

    int max=0;

    for (set<int>::iterator it = numSet.begin();it != numSet.end();++it) {
        int i = *it;
        if (numSet.find(i-1)!=numSet.end())
            continue;
        int current = i;
        int curr_len=1;
        while (numSet.find(current+1)!=numSet.end()) {
            current++;
            curr_len++;
        }
        if (max<curr_len) max=curr_len;
    } 
    return max;
}