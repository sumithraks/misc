#include <iostream>
#include <vector>
using namespace std;

vector<int> pair_sum(const vector<int> &nums,const int target) {
    if (nums.size() < 2) return {};
    unsigned int left=0,right=static_cast<int>(nums.size()) - 1;;
    int sum=0;
    vector<int> result;
    while (left <right) {
        sum=nums[left]+nums[right];
        if (sum==target) {
            result.push_back(left);
            result.push_back(right);
            return result;
        }
        if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    return result;
}

struct TestCase {
    string name;
    vector<int> nums;
    int target;
    bool expectFound;
};


bool check(const TestCase& tc) {
    vector<int> result = pair_sum(tc.nums, tc.target);
    if (!tc.expectFound) return result.empty();
    if (result.size() != 2) return false;
    int i = result[0], j = result[1];
    return tc.nums[i] + tc.nums[j] == tc.target;
}

int main() {
    vector<TestCase> tests = {
        {"basic pair",          {-5, -2, 3, 4, 6}, 7,  true},
        {"negatives only",      {-8, -3, -1},      -4, true},
        {"no pair exists",      {1, 2, 3},         10, false},
        {"empty input",         {},                0,  false},
        {"single element",      {5},               5,  false},
        {"duplicates",          {1, 1, 4},         2,  true},
    };

    int passed = 0;
    for (const auto& tc : tests) {
        bool ok = check(tc);
        cout << (ok ? "PASS " : "FAIL ") << tc.name << "\n";
        if (ok) ++passed;
    }
    cout << passed << "/" << tests.size() << " passed\n";
}