package Vigenere;

/**
 * Functions and methods used to solve problem 6.
 * This program is not fully-automatic, it requires manual revision of outputs.
 * All such places has been commented in code and explained in the assignment.
 * 
 * Note that all files are being shared in the package 
 * for the purpose of function classification. 
 * e.g. frequencyAnalysis for counting 
 * and vigenereCipher for character shifting.
 * @author Jinqiu Liu
 * @collaborator Ronnie Yuan
 */

public class problem6 {
    public static String encryptedText = "hóóvskirafsípránxraatóóhrlóáæpdéuaýþm"
            + "dxajröðéábiðhjéurkmýæóðætúrævsöáúáóðhöþsgeéjyönjglheþdaouhxyaxeó"
            + "éoóímýæsðsóýlþævlfðsúðhkhpnþeóémgffjrhxnóórskrmíýjéuúeóeæuðutdýe"
            + "nryúötöúllæöaxárymröþxdáujxerílivexxöúoþlæyöxvýóavtelébvdsaexfmæ"
            + "étyksbnpkfðvóýlsképnajvlróolraújtðidgnxenrtúhshspgaruykóækiðétau"
            + "aelliaabðyueýmævmvhæaéáóeeebýuaevxsrnýxpkbbþxyóaóetóxvgpptyksbnp"
            + "kfðbýýhhnyövhoéduddxaktapþentiöenhkyihtíæsxrýsöþnvdsðjybúínvfnún"
            + "ýfivbðöþfxserívpirxbýýaehelexýburkmýæehjélðötégðxnvkgjkiæölafxáé"
            + "áráafyóbgökbmeerællúvixvxbgfeömiósxrýuvsþexkýævdajáýmðfvdþieðdaé"
            + "xaíörsk";
    public static char[] p6Chars = {
        'a','á','b','d','ð','e','é','f',
        'g','h','i','í','j','k','l','m',
        'n','o','ó','p','r','s','t','u',
        'ú','v','x','y','ý','þ','æ','ö'};
    public static void main(String[] args) {
        //Step 1: look into all key lengths
        for (int i = 3; i <= 0; i++) {
            System.out.println("Interval = " + i + "----------------");
            frequencyAnalysis cipherText = 
                new frequencyAnalysis(encryptedText,i,p6Chars);        
            cipherText.print(true);
            System.out.println();
        }        
        
        //Step 2: try to figure out what the key is.

        //traditional keygen
        for (int l = 10; l <= 10; l++) {
            System.out.println("Trying l = " + l);
            int length = l;
            int threshold = 7;
            frequencyAnalysis cipherText = 
                    new frequencyAnalysis(encryptedText,length,p6Chars);
            String[] caafts = new String[length];
            int botT[] = new int[length];

            //caafts = CharacterArrayArrangedFrequentlyToStrings
            //making abbreviations is fun
            for (int i = 0; i < length; i++) {
                //System.out.println("POS = " + i);
                caafts[i] = cipherText.fcString(i);
                //System.out.println(caafts[i]);
                botT[i] = cipherText.zero(i);
            }
            System.out.println("Attempt to find key");
            for (int i = 0; i < length; i++) {
                System.out.print("Key position " + i + ": ");
                System.out.print("[MatchTop] ");
                String top = matchTop(caafts[i],threshold);
                System.out.print(top);
                System.out.print(" [MatchX] ");
                String x = forceMatchX(caafts[i],botT[i]);
                System.out.print(x);
                System.out.print(" [intersection] ");
                String intersection;
                intersection = intersect(top, x);
                System.out.print(intersection);                
                System.out.println();
            }
            System.out.println();
        }
        
        //single key testing
        //Step 3: enter the key and see if the decrypted data makes sense
        String decryptedText = vigenereCipher.decode(p6Chars, 
                encryptedText, "jólasvéinn");
        frequencyAnalysis plaintText = 
                new frequencyAnalysis(decryptedText,1,p6Chars);
        plaintText.print(false);
        
        //Step 4: print out the final decrypted message and fine-tune the key
        System.out.println("123456789X123456789X123456789X123456789X");
        for (int i = 0; i < decryptedText.length(); i++) {
            System.out.print(decryptedText.charAt(i));
            if ((i+1)%40 == 0)
                System.out.println();
        }
        System.out.println();      
    }    
    
    /**
     * takes in 12 characters and shifts all of them 
     * until best match to "rainesgulðtm".
     * 12 instead of 8 because the set of alphabets is larger than English.
     * @param caaft The caaft string.
     * @param threshold least number of matches to be included in the return
     * @return The string that provide the best match as a key
     */
    public static String matchTop(String caaft, int threshold) {
        String best12 = "rainesgulðtm";
        String top12 = caaft.substring(0, 12);
        int[] matchScore = new int[32];
        for (int i = 0; i < 32; i++) {
            String key = String.valueOf(p6Chars[i]);
            String encoded = vigenereCipher.decode(p6Chars,top12,key);
            matchScore[i] = matchUp(encoded,best12);
        }
        String keys = "";
        for (int i = 0; i < 32; i++) {
            if (matchScore[i] >= threshold) {
                keys+=p6Chars[i];
            }
        }
        return keys;
    }   
    
    /**
     * forces 'x' to be in the last few positions
     * @param caaft The caaft string.
     * @param threshold up until which position is allowed. 
     * where 1 means must be last position
     * @return The string that provide all matches as a key
     */
    public static String forceMatchX(String caaft, int threshold) {
        String botX = caaft.substring(32-threshold, 32);
        int[] matchScore = new int[32];
        for (int i = 0; i < 32; i++) {
            String key = String.valueOf(p6Chars[i]);
            String encoded = vigenereCipher.decode(p6Chars,botX,key);
            matchScore[i] = matchUp(encoded,"x");
        }
        String keys = "";
        for (int i = 0; i < 32; i++) {
            if (matchScore[i] >= 1) {
                keys+=p6Chars[i];
            }
        }
        return keys;
    }
    
    /**
     * Compares 2 Strings and see how many characters match.
     * e.g. abcde and dexyz has 2 matches.
     * @param a a String.
     * @param b another String.
     * @return number of matches.
     */
    private static int matchUp(String a, String b) {
        int score = 0;
        for (int i = 0; i < 32; i++) {
            String c = String.valueOf(p6Chars[i]);
            if (a.contains(c) && b.contains(c)) {
                score++;
            }
        }
        return score;
    }

    private static String intersect(String a, String b) {
        String intersection = "";
        for (int i = 0; i < 32; i++) {
            String c = String.valueOf(p6Chars[i]);
            if (a.contains(c) && b.contains(c)) {
                intersection += c;
            }
        }
        return intersection;
    }

}
