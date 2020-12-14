import java.util.Scanner;

public class Brackets {
    public static void main(String[] args) {
        int n;
        Scanner scanner = new Scanner(System.in);
        while (true){
            try{
            System.out.print("Enter integer N >= 0: ");
            n = scanner.nextInt();
            if (n < 0){
                throw new NumberFormatException();
            }
            } catch (Exception ex) {
                System.out.println("Incorrect input. Enter integer 0<n<=10000 ");
                continue;
            } 
            break;
        }
        scanner.close();
        System.out.println(n);
        System.out.println("Number of valid balanced parentheses is " + calculate(n));

    }

    /*
     * C[n] - number of valid balanced parentheses of length 2n;
     * C[0] = 1 (empty sequence);
     * The recurrent formula for Cn is:
     * C[n] = C[0]C[n - 1] + C[1]C[n - 2] + C[2]C[n - 3] +...+ C[n - 2]C[1] + C[n - 1]C[0];
     *
     *
     * */
    private static long calculate(int n) {
        long[] c = new long[n + 1]; //the array to store C[m]
        c[0] = 1;
        for (int m = 1; m <= n; ++m) { //calculate C[m] for m=1..n
            c[m] = 0;
            for (int k = 0; k < m; ++k) {
                c[m] += c[k] * c[m - 1 - k];
            }
        }
        return c[n];
    }
}
