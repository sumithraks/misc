#include <iostream>
#include <string>
#include <map>

using namespace std;
int characterReplacement(string s, int k) {
    map<char,int> frequency;
    int highest_freq = 0;
    int max_window=0;
    int num_chars_to_replace=0;
    int right=0;int left=0;
    while (right < int(s.length())) {
        if (frequency.find(s[right])!=frequency.end())
            frequency[s[right]]++;
        else
            frequency[s[right]]=1;
        highest_freq=max(highest_freq,frequency[s[right]]);
        num_chars_to_replace=right-left+1-highest_freq;
        if (num_chars_to_replace>k) {
            frequency[s[left]]=frequency[s[left]]-1;
            left+=1;
        }
        max_window=right-left+1;
        right++;
    }
    return max_window;
        
}

int main(int argc,char **argv) {
    cout << characterReplacement("ABAB",2);
    return 0;
}