

public class Factorial {
    public static void main(String[] args) {
        factorial();
    }
    public static void factorial(){
        int[] dig = new int[10000];
        int first = 0;
        int last = 0;
        int sum = 0;
        int carry;
        int n;
        int x;

        dig[0] = 1;
        // Bitwise (поразрядное?) multiplication of numbers from 2 to 99,
        // Multiply by 100 does not make sense - it will only add zeros to the result
        for (n = 2; n <= 99; n++) {
            carry = 0;
            for (x = first; x <= last; x++) {
                carry = dig[x] * n + carry; // multiply each digit by n
                dig[x] = carry % 100000;    // 100000 - base
                if (x == first && (carry % 100000) == 0) { // reduce unnecessary multiplications by zero
                    first++;
                }
                carry /= 100000; // carry value
            }
            if (carry != 0) {
                dig[++last] = carry; // if there will be a carry after multiply all digits - add one more digit
            }
        }
        for (x = first; x <= last; x++) { // bitwise sum of all digits of the resulting number
            sum += dig[x] % 10 +
                    (dig[x] / 10) % 10 +
                    (dig[x] / 100) % 10 +
                    (dig[x] / 1000) % 10 +
                    (dig[x] / 10000) % 10;
        }
        System.out.println("Sum: " + sum);
    }
}
