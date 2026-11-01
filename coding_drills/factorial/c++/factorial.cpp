#include <iostream>

using namespace std;
long long factorial(unsigned long n) {
    if (n<=1) return 1;
    return n*factorial(n-1);
}

int main() {
    cout << "\nFactorial of 10 is " << factorial(10);
    cout << "\nFactorial of 5 is " << factorial(5);
}