import java.util.Scanner;

public class Palindrome {
    public static boolean isPalindrome(String s) {
        if (s.isBlank()||s.isEmpty())
            return true;
        int left=0,right=s.length()-1;
        while (left < right) {
            while (!Character.isLetterOrDigit(s.charAt(left)) && left < right)
                left++;
            while (!Character.isLetterOrDigit(s.charAt(right)) && left < right)
                right--;
            if (s.charAt(right)!=s.charAt(left))
                return false;
            left++;
            right--;
        }   
        return true;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Enter the string");
        String s=scanner.nextLine();
        System.out.println("Input is"+(isPalindrome(s)?" ":" not ")+"palindrome");
        scanner.close();
    }
}