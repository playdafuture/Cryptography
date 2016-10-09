package fermatNumber;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Jinqiu Liu
 */
public class strongPrime {
    static BigInteger p;
    //Frequently used constants
    static final BigInteger ZERO = BigInteger.ZERO;
    static final BigInteger ONE = BigInteger.ONE;
    static final BigInteger TWO = new BigInteger("2");

    public static void main(String[] args) {
        BigInteger root;
        System.out.println("Trying to find a root");
        while (true) {
            do { 
                root = generateP(); //find a 63B (504b) prime to use as a "root"                
            } while (findFactor(root,8).compareTo(ONE) != 0);
            System.out.println("Root = \n" + root);
            for (int i = 2; i < 256; i+=2) {
                for (int j = 2; j < 256; j+=2) {
                    BigInteger rORs = root.multiply(new BigInteger(Integer.toString(i))).add(BigInteger.ONE);
                    if (findFactor(rORs,8).compareTo(ONE) == 0) { //rORs is prime
                        //use this as left root
                        p = rORs.multiply(new BigInteger(Integer.toString(j))).add(BigInteger.ONE);
                        if (findFactor(p,8).compareTo(ONE) == 0) { //p is prime
                            System.out.println("Candidate @ i = " + i + " && j = " + j);
                            if(isStrong(p.add(ONE), 4)) {
                                System.out.println("WE HAVE A WINNER!");
                                System.out.println("p = \n"+ p.toString());
                                System.exit(0);
                            }                            
                        }
                        //use this as right root
                        p = rORs.multiply(new BigInteger(Integer.toString(j))).subtract(BigInteger.ONE);
                        if (findFactor(p,8).compareTo(ONE) == 0) { //p is prime
                            System.out.println("Candidate @ i = " + i + " && j = " + j);
                            if (isStrong(p.subtract(ONE), 4)) {
                                System.out.println("WE HAVE A WINNER!");
                                System.out.println("p = \n" + p.toString());
                                System.exit(0);
                            }                            
                        }
                    } else {
                        //rORs is not prime anyway, need to skip to next i
                        j = 256;
                    }
                }
            }
            System.out.println("Time to find another root");
        }  
    }    
    
    
    public static boolean isStrong(BigInteger n, int strength) {        
        BigInteger biggerFactor = n, smallerFactor=ONE;
        
        while (biggerFactor.bitLength() > 500) {
            BigInteger f = findFactor(biggerFactor,strength);
            BigInteger g = n.divide(f);
            
            if (f.compareTo(ONE)==0 || g.compareTo(ONE)==0) {
                //biggerFactor is prime
                break;
            }
            
            if (f.bitLength() < g.bitLength()) {
                biggerFactor = biggerFactor.divide(f);
                smallerFactor = smallerFactor.multiply(f);
            } else {
                biggerFactor = biggerFactor.divide(g);
                smallerFactor = smallerFactor.multiply(g);
            }
        }
        
        if (smallerFactor.bitLength() > 12) {
            System.out.println("Failed level 1 strong test");
            return false;
        }
        
        //if we get here then bigger factor is good enough
        biggerFactor = biggerFactor.subtract(ONE);
        smallerFactor = ONE;
        
        //do the test again with a lower bit limit        
        while (biggerFactor.bitLength() > 450) {
            BigInteger f = findFactor(biggerFactor,strength);
            BigInteger g = n.divide(f);
            
            if (f.compareTo(ONE)==0 || g.compareTo(ONE)==0) {
                //biggerFactor is prime
                break;
            }
            
            if (f.bitLength() < g.bitLength()) {
                biggerFactor = biggerFactor.divide(f);
                smallerFactor = smallerFactor.multiply(f);
            } else {
                biggerFactor = biggerFactor.divide(g);
                smallerFactor = smallerFactor.multiply(g);
            }
        }
        
        if (smallerFactor.bitLength() > 52) {
            System.out.println("Failed level 2 strong test");
            return false;
        }
        
        //if we get here then left root also pass!
        return true;
    }
    
    /**
     * Tests and see if n is a composite number. ASSUMES n is odd.
     * @param num The number to be tested.
     * @param certainty How sure do you want it to be?
     * @return TRUE if n is composite, and FALSE if n is probably prime.
     */
    public static BigInteger findFactor(BigInteger num, int certainty) {
        if (num.mod(TWO).compareTo(BigInteger.ZERO) == 0) {
            return TWO;
        }
        //m = num - 1
        BigInteger m = num.subtract(BigInteger.ONE);
        //factor m into the form of 2^k * m
        int k = 0;        
        //BigInteger quotient;
        while (m.mod(TWO).compareTo(BigInteger.ZERO) == 0) {
            m = m.divide(TWO);            
            k++;
        }
        //finished calculating k and m
        for (int i = 0; i < certainty; i++) { //witness loop
            int rand = (int) (Math.random() * 65536);            
            BigInteger a = new BigInteger(Integer.toString(rand));
            a = power(a,m,num); //a = a^m (mod num)
            BigInteger f = MRTest(a,k-1,num);
            if (f.compareTo(ONE)!=0) { 
                // f!=1, f^2=1(mod num)
                f = f.add(ONE);
                return polandSpring.gcd(f, num);
            }
        }
        //finished all certainty rounds and still no factor found
        return ONE;        
    }
    
    /**
     * MillerRabin Test.
     * @param a The base number, this should be the chosen random number 
     * to a certain power from the previous step.
     * @param r How many rounds to check, this depends on the value of 
     * k in 2^k as a factor of n-1
     * @param n The prime number to be determined
     * @return TRUE if it's a certain composite, FALSE if the round is passed
     */
    private static BigInteger MRTest(BigInteger a, int r, BigInteger n) {
        BigInteger minusOne = n.subtract(BigInteger.ONE);
        
        for (int i = 0; i < r; i++) {
            BigInteger previousTerm = a;
            a = a.multiply(a).mod(n);
            
            if (a.compareTo(ONE) == 0 && previousTerm.compareTo(minusOne) != 0) {
                //composite. return the previous term to caller for factorization
                return previousTerm;
            } else if (a.compareTo(ONE) == 0) {
                //a became 1 and previous term is -1
                return ONE;
            }
        }
        //probably prime
        return ONE;
    }    

    /**
     * Calculates base to the x (mod n).
     * @param base the base
     * @param x the exponent
     * @param n the modulus
     * @return 2^m(mod n)
     */
    private static BigInteger power(BigInteger base, BigInteger x, BigInteger n) {
        if (x.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ONE;
        } else if (x.compareTo(BigInteger.ONE) == 0) {
            return base;
        } else {
            BigInteger CP = BigInteger.ONE;
            BigInteger temp = base;
            BigInteger lastStep = temp;
            while (CP.compareTo(x) < 0) { //CP < x
                lastStep = temp;
                temp = temp.multiply(temp).mod(n);
                CP = CP.multiply(TWO);
            }
            BigInteger toGo = power(base, x.subtract(CP.divide(TWO)), n);
            return lastStep.multiply(toGo).mod(n);
        }
    }
    
    /**
     * PseudoRandomly generates a 62 Byte number.
     * @return An odd number of 62 Bytes.
     */
    public static BigInteger generateP() {
        Random r = new Random();
        int size = 64; //63B = 504b        
        byte[] b = new byte[size]; 
        r.nextBytes(b);
        b[0] = 0; //force the first byte to be 0, so that p is positive
        if (Byte.toUnsignedInt(b[size-1]) %2 == 0) {
            b[size-1]++; //force the last byte is odd, so that p is odd
        }
        return new BigInteger(b);
    }
}
