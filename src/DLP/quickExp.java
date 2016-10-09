package DLP;

import java.math.BigInteger;

/**
 * Calculates big (60 bit or less) exponents mod something quickly.
 * @author Jinqiu Liu
 */
public class quickExp {
    static BigInteger[] exponent;
    static BigInteger[] value;
    public static long calculate(long base, long power, long modulos) {
        BigInteger p = bi(modulos);
        
        int size = 60;
        exponent = new BigInteger[size];
        value = new BigInteger[size];
        
        BigInteger tempE = bi(1);
        BigInteger tempV = bi(base);
        for (int i = 0; i < size; i++) {
            exponent[i] = tempE;
            value[i] = tempV;
            tempE = tempE.multiply(bi(2));
            tempV = tempV.multiply(tempV);
            tempV = tempV.mod(p);
        }
        
        BigInteger ans = bi(1);
        while (power > 0) {
            for (int i = size-1; i>=0; i--) {
                if (power >= exponent[i].longValueExact()) {
                    power -= exponent[i].longValueExact();
                    ans = ans.multiply(value[i]);
                    ans = ans.mod(p);
                }
            }
        }
        return (ans.longValueExact());
    }
    
    public static BigInteger bi(int i) {
        return new BigInteger(Integer.toString(i));
    }
    
    public static BigInteger bi(long l) {
        return new BigInteger(Long.toString(l));
    }
}
