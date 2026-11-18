#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

int search(string pat, string txt) {
    if (pat.length()==0 || pat.empty())
        return 0;
    
    if (txt.length()==0 || txt.empty())
        return 0;

    if (txt.length() < pat.length())
        return 0;

    int pat_cnt[26]={};
    int txt_cnt[26]={};
    
    for (int i=0;i<pat.length();++i) {
        int index = pat[i]-'a';
        pat_cnt[index]++;
    }

    int left=0, right=0;
    int index = 0;
    int num_anagrams = 0;
    while (right < txt.length()) {
        index=txt[right]-'a';
        txt_cnt[index]++;
        if (right-left+1==pat.length())
        {
            bool equals = std::equal(std::begin(txt_cnt),std::end(txt_cnt),std::begin(pat_cnt),std::end(pat_cnt));
            if (equals)
                num_anagrams++;
            index=txt[left]-'a';
            txt_cnt[index]--;
            left++;
        }
        right++;
    }
    return num_anagrams;
}

int main(int argc,char **argv) {
    string txt = "bacdgabcda";
    string pat = "abcd";
        //AnagramFinder.search(pat, txt);
    cout << search(pat, txt) << endl;
    txt="aaababaa";
    pat="aaba";
    cout << search(pat, txt)<< endl;
    cout << search("", txt)<< endl;
    cout << search(pat, "")<< endl;
    cout << search("","")<< endl;
    cout << search(txt, pat)<< endl;
    pat = "zzz";
    cout << search(pat, txt)<< endl;
}