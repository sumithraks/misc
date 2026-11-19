#include <iostream>
#include <vector>
using namespace std;

int min_cutting_work(vector<int> heights, int k) {
    int left=0;
    int right = heights.size();
    while (left < right) {
        int mid = (left+right)/2+1;
        if (cuts_enough(mid,k,heights)) {
            left=mid;
        }
        else {
            right=mid-1;
        }
    }
    return right;
}

bool cuts_enough(int mid,int k,vector<int> heights) {
    int collected = 0;
    for (int i:heights) {
        if (i>=mid) {
            collected+=(i-mid);
        }
    }
    return collected >= k;
}

int main(int argc,char **argv) {
    vector<int> heights = {2,6,3,8};
    int k = 7;
    cout << "Min " << min_cutting_work(heights,k) << endl;;
}