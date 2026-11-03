#include <iostream>
#include <string>
#include <cctype> 

using namespace std;
bool isValidPalindrome(string input) {
    if (input.empty())
        return true;
    unsigned int left=0;
    unsigned int right=input.length()-1;
    while (left < right) {
        while (!std::isalnum(input.at(left)) && left < right) 
            left++;
        while (!std::isalnum(input.at(right)) && left < right) 
            right--;
        if (input.at(left)!=input.at(right))
            return false;
        left++;
        right--;
    }
    return true;
}

int main(int argc,char **argv) {
    string input;
    cout << "Enter String to check: ";
    cin>>input;
    cout << "Input is"<<(isValidPalindrome(input)?" ":" not ")<<" palindrome";
}