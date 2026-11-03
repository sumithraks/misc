#include <iostream>
#include <vector>
using namespace std;

void move_zeros(vector<int> &input) {
    int left=0;
    int right=left;
    for (right=0;right < input.size();++right) {
        if (input[right]!=0) {
            int a = input[left];
            input[left]=input[right];
            input[right]=a;
            left++;
        }
    }
}

int main() {
    vector<int> nums = {0,1,0,3,12};
    move_zeros(nums);
    for (vector<int>::iterator it = nums.begin(); it != nums.end();++it)
       cout << *it << endl;
}