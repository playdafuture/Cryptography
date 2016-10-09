package fermatNumber;

import java.math.BigInteger;

/**
 *
 * @author Jinqiu Liu
 */
public class verify {
    static final BigInteger ZERO = BigInteger.ZERO;
    static final BigInteger ONE = BigInteger.ONE;
    static final BigInteger TWO = new BigInteger("2");

    public static void main(String[] args) {
        BigInteger p = new BigInteger("203603902473233733839466405301666149088469103665962840774896823510304707365596544849552414596658149900162497914817511727722049497022710815623115981493207");
        System.out.println("Testing primality on \n" + p);
        if (strongPrime.findFactor(p, 512) != ONE) {
            System.out.println("Oops, p is actually a composite");
        } else {
            System.out.println("Good news! p passed 512 MR Test");
        }
        System.out.println("\nTesting p-1");
        BigInteger pm1 = p.subtract(BigInteger.ONE);
        testStrong(pm1);
        System.out.println("\nTesting p+1");
        BigInteger pp1 = p.add(BigInteger.ONE);
        testStrong(pp1);
    }
    
    public static void testStrong(BigInteger n) {
        System.out.println("First step: Level 1 factors");
        System.out.println("Trying to factor \n" + n);
        BigInteger biggerFactor = n, smallerFactor=ONE;
        
        while (biggerFactor.bitLength() > 500) {
            BigInteger f = strongPrime.findFactor(biggerFactor,8);
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
        
        System.out.println("The factors are");
        System.out.println(biggerFactor);
        System.out.println("("+biggerFactor.bitLength()+" bits)");
        System.out.println("and");
        System.out.println(smallerFactor);
        System.out.println("("+smallerFactor.bitLength()+" bits)");
        
        if (smallerFactor.bitLength() > 12) {
            System.out.println("Failed level 1 strong test");
            return;
        }
        
        System.out.println("Next step: Level 2 factors");
        
        //if we get here then bigger factor is good enough
        biggerFactor = biggerFactor.subtract(ONE);
        smallerFactor = ONE;
        System.out.println("Trying to factor \n" + biggerFactor);
        //do the test again with a lower bit limit        
        while (biggerFactor.bitLength() > 450) {
            BigInteger f = strongPrime.findFactor(biggerFactor,8);
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
            return;
        }
        
        System.out.println("The factors are");
        System.out.println(biggerFactor);
        System.out.println("("+biggerFactor.bitLength()+" bits)");
        System.out.println("and");
        System.out.println(smallerFactor);
        System.out.println("("+smallerFactor.bitLength()+" bits)");
        
        //if we get here then left root also pass!
        System.out.println("Congratulations! It's all good!");
    }
}
