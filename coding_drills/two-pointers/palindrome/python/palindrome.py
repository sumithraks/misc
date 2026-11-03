def palindrome(s:str) -> None:
    if (len(s)==0):
        print("String is palindrome")
        return
    left=0;right=len(s)-1
    while (left<right):
        while (left<right and not s[left].isalnum()):
            left+=1
        while (left<right and not s[right].isalnum()):
            right-=1
        if (s[left]!=s[right]):
            print("String is not palindrome")
            return
        left+=1
        right-=1
    print("String is a palindrome")

def main() -> None:
    s = input("Enter the string: ")
    palindrome(s)

if __name__ == "__main__":
    main()

                