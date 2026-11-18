#include <iostream>
#include <string>
#include <map>

using namespace std;
int lengthOfLongestSubstring(string s) {
    map<char,int> charset;
    int left=0;int right=0;
    int max=0;
    if (s.empty())
        return 0;
    while (right < int(s.length())) {
        
        if (charset.find(s.at(right))!=charset.end() && charset[s.at(right)] >= left) 
            left = charset[s.at(right)];
        if (max < right-left+1)
            max = right-left+1;
        charset[s.at(right)]=right;
        right++;
    } 

    return max;
}

int main(int argc, char** argv) {
    cout << (lengthOfLongestSubstring("s")) << endl;
    cout  << (lengthOfLongestSubstring("abcabcbb")) << endl;
    return 0;
}