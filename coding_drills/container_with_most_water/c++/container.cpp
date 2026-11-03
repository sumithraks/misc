#include<iostream>
#include<vector>
using namespace std;
unsigned int max_volume(const vector<int>&heights) {
    int area=0;
    int curr_area=0;
    int left=0, right=heights.size()-1;
    while (left<right) {
        
        curr_area = min(heights[left],heights[right])*(right-left);
        //cerr << "Left="<<left<<" right "<<right<<curr_area <<endl;
        area = max(curr_area,area);
        if (heights[left]<heights[right])
            left++;
        else
            right--;
    }
    return area;
}

int main(int argc,char**argv) {
    vector<int> test1={1,8,6,2,5,4,8,3,7};
    cout << max_volume(test1);
}