package fermatNumber;

import java.math.BigInteger;

/**
 *
 * @author Jinqiu Liu
 */
public class bruteForce {
    static BigInteger F_6 = new BigInteger("18446744073709551617");
    public static void main(String[] args) {
        BigInteger factor = new BigInteger("3");
        long max = 4294967296L;
        BigInteger remainder;
        BigInteger two = new BigInteger("2");
        BigInteger four = new BigInteger("4");
        while (factor.longValueExact() < max) {
            //3 (mod 10)
            remainder = F_6.mod(factor);
            if (remainder.intValueExact() == 0) {
                System.out.println("Factor found! " + factor.longValueExact());
                break;
            }
            //Skipping 5 because the last digit of F_6 is obviously not 5 or 0
            factor = factor.add(four);
            //7 (mod 10)
            remainder = F_6.mod(factor);
            if (remainder.intValueExact() == 0) {
                System.out.println("Factor found! " + factor.longValueExact());
                break;
            }
            factor = factor.add(two);
            //9 (mod 10)
            remainder = F_6.mod(factor);
            if (remainder.intValueExact() == 0) {
                System.out.println("Factor found! " + factor.longValueExact());
                break;
            }
            factor = factor.add(two);
            //1 (mod 10)
            remainder = F_6.mod(factor);
            if (remainder.intValueExact() == 0) {
                System.out.println("Factor found! " + factor.longValueExact());
                break;
            }
            factor = factor.add(two);
        }
    }
}
