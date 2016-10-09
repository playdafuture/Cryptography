package fermatNumber;

import java.math.BigInteger;

/**
 *
 * @author Jinqiu Liu
 */
public class polandSpring {
    static BigInteger F_6 = new BigInteger("18446744073709551617");
    static BigInteger n;
    static BigInteger x;
    static BigInteger y;
    static BigInteger c;
    public static void main(String[] args) {
        System.out.println(
                factor(F_6,new BigInteger("105"),new BigInteger("65537")));
    }
    
    /**
     * Calculates one of the factors using the Pollard's rho method.
     * @param n1 The number that wants to be factored
     * @param x1 arbitrary number x
     * @param c1 arbitrary number c
     * @return A factor of n1, 1 if n1 is prime and 0 if this method has failed.
     */
    public static BigInteger factor(BigInteger n1, BigInteger x1, BigInteger c1) {
        n = n1;
        x = x1;
        c = c1;
        y = x;
        BigInteger ans;
        do {
            x = f(x);
            y = f(f(y));
            ans = gcd(x,y);
        } while (ans.compareTo(BigInteger.ONE) == 0);
        if (ans.compareTo(n) == 0) {
            //this should rarely happen. x and c needs a different value
            return BigInteger.ZERO;
        } else {
            return gcd(x,y);
        }        
    }
    
    public static BigInteger f(BigInteger x) {
        //f(x) = x^2 + c (mod n)
        BigInteger ans = x.multiply(x);
        ans = ans.add(c);
        ans = ans.mod(n);
        return ans;
    }
    
    public static BigInteger gcd(BigInteger x, BigInteger y) {
        if (x.compareTo(y) < 0) { //x < y
            return y.subtract(x).gcd(n); //gcd([y-x],c)
        }
        return x.subtract(y).gcd(n); //gcd([x-y],c)
    }
}
