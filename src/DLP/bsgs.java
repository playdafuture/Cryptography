package DLP;

import java.math.BigInteger;

/**
 * Baby-Step Giant-Step main class
 * @author Jinqiu Liu
 */
public class bsgs {
    public static void main(String[] args) {
        //problem: base ^ x = c (mod p), solve for x
	int base = 2;
        long c = Long.parseUnsignedLong("92327518017225");
        long p = Long.parseUnsignedLong("247457076132467");
        //step 0: pick k
	int k = 9999999; //arbitrary number
	//step 1: evaluate (base^i) for i = 1..k (and store them in memory)
        //if 2^x = a, then babyStep[x] = a
        binaryTree babyStepT = new binaryTree();	
        BigInteger num = bigInt(1);
	for (int i = 0; i < k; i++) {
            //babyStep[i] = num;
            babyStepT.add(i, num.longValueExact());
            //num *= base;
            num = num.multiply(bigInt(base));
            //num %= p;
            num = num.mod(bigInt(p));
	}
	System.out.println("Baby steps complete!");
	
	//step 2: let giantStep = baseInverse^k, evaluate c*(giantStep^j) for j = 1...
        //2.1 find the inverse of base (mod p) i.e. base*inverse = 1 (mod p)
        long inverse = EEA(base,p)[1];
        if (inverse < 0) {
            inverse += p;
        }
        System.out.println("Inverse = " + inverse);
        //2.2 find giantStep
        long gs = quickExp.calculate(inverse, k, p);
        BigInteger giantStep = bigInt(gs);
        System.out.println("GiantStep = inverse^"+k+"="+giantStep.longValueExact());
        //giantStep = inverse^k = base^-k
        //2.3 rotate j until answer found
        BigInteger ans = giantStep.multiply(bigInt(c));
        //ans = c*base^-jk, j initially 1 going to "infinity"
        long m=0,n=0;
        for (int j = 1; true; j++) {
            //ans %= p;
            ans = ans.mod(bigInt(p));
            m = babyStepT.find(ans.longValueExact());
            if (m != -1) {
                n = j;
                System.out.println("Answer found at j = " + j);
                System.out.println("base^"+m+" = " + ans.longValueExact());
                System.out.println("c*base^-"+k+"*"+j+" = " + ans.longValueExact());
                break;
            }
            //ans *= giantStep;
            ans = ans.multiply(giantStep);
        }        
	//return x = jk+i (i=m, j=n)
        System.out.println("Therefore,");
        System.out.println("m=" + m); //2^m = match
        System.out.println("k=" + k); //pre-chosen k value
        System.out.println("n=" + n); //c*giantStep^n = match
        BigInteger x = bigInt(k).multiply(bigInt(n));
        x = x.add(bigInt(m));
        System.out.println("x = " + x);
        System.out.print("Verification: ... ");
        long verify = quickExp.calculate(base, x.longValueExact(), p);
        if (verify == c) {
            System.out.println("PASSED!");
        } else {
            System.out.println("FAILED!");            
        }
        System.out.println(base + "^" + x + "(mod " + p + ") = " + verify);
    }
    
    /**
     * Extended Euclidean Algorithm.
     * @param a Integer a
     * @param b Integer b
     * @return An array that contains the answer
     * ans[0] = GCD of a and b;
     * ans[1] = inverse of a;
     * ans[2] = inverse of b;
     */
    public static long[] EEA(long a, long b) { 
        long[] ans = new long[3];
        long q;

        if (b == 0)  {
            ans[0] = a; //gcd
            ans[1] = 1; //inverse of a
            ans[2] = 0; //inverse of b
        } else {     /*  Otherwise, make a recursive function call  */ 
            q = a/b;
            ans = EEA (b, a % b);
            long temp = ans[1] - ans[2]*q;
            ans[1] = ans[2];
            ans[2] = temp;
        }

        return ans;
    }

    private static BigInteger bigInt(long l) {
        return new BigInteger(Long.toUnsignedString(l));
    }
}
