public  class Factorial {
    public static long factorial(long n) {
        if (n<=1) {
            return 1;
        }
        return n*factorial(n-1);
    }

    public static void main() {
        System.out.println("Factorial of 10 is " + factorial(10));
        System.out.println("Factorial of 5 is " + factorial(5));
    }
}