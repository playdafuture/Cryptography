package Vigenere;

/**
 * Functions and methods used to solve problem 5.
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

public class problem5 {
    public static String encryptedText = "sqmvwtezknehmqljusenscippgmblcumoufor"
            + "qswulzcrxxtzrxnboaeqxvyfhrpcldaimteqdhbuzrqmpiefgibpfxrsogpmqjdm"
            + "lpuiatldhibipoztnsdzhqkmjumeubokrgqbxndvcpetdpjtnuumxvdbkijttzfs"
            + "obwgsikveusljulymsctsmoimgzdrtxseuuicstojwwpcyzhnuzonyaulzzqxszg"
            + "rpxpvumkpmlermcilfzqavoqkcbulyoimbypvewuwauibnlvdwczearxavendjxs"
            + "pmvewuzzzqkmtzfrhnathxqbemlgdsemhpnezrslrtqmhvyszbnvcjzzblnbeqcs"
            + "ogpmsyafmkcmbtpyaprorzzxdsppdjxsxqcywgtzhwqfoedrccprnvnnjfhqnjyf"
            + "nxqjdnqijusumkfpcxcwlbcodljmqyzhnvammhcilfrsubxqkcjoogmjjtsunrjc"
            + "wqsljuoafwkbcwzxvflehljmenxxqfxigcrjyfgmbxpmjtrqtzfxrnpaetnbnqge"
            + "efyaciujrtsxxqlerefbjfgicjxq";
    public static char[] englishChars = {'a','b','c','d','e','f','g','h','i',
        'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static void main(String[] args) {
        //Step 1: look into all key lengths
        for (int i = 3; i <= 12; i++) {
            System.out.println("Interval = " + i + "----------------");
            frequencyAnalysis cipherText = 
                new frequencyAnalysis(encryptedText,i,englishChars);        
            cipherText.print(true);
            System.out.println();
        } 
        /**
         * we reviewed all the output with length 3 to 12,
         * length 12 is the one we liked the most 
         * since it has the most number of 0s in the end.
         * Anything above this would be commented out when working on step 2.
         */
        int length = 12;
        frequencyAnalysis cipherText = 
                new frequencyAnalysis(encryptedText,length,englishChars);
        
        //Step 2: try to figure out what the key is.
        /**
         * The goal here is to shift the alphabets so that 
         * the most common letters of English matches 
         * the highest frequency letters in the chart.
         */
        String[] caafts = new String[length]; 
        //caafts = CharacterArrayArrangedFrequentlyToStrings
        //making abbreviations is fun
        for (int i = 0; i < length; i++) {
            System.out.println("POS = " + i);
            caafts[i] = cipherText.fcString(i);
            System.out.println(caafts[i]);
        }
        System.out.println("Attempt to find key");
        for (int i = 0; i < length; i++) {
            String k = match8(caafts[i]); //here we use a method called match8
            System.out.println(k);
        }
        
        //Step 3: enter the key and see if the decrypted data makes sense
        String decryptedText = vigenereCipher.decode(englishChars, 
                encryptedText, "mzejbl"); 
        //the algorithm produced "mzljblmmejbl" as the best key
        frequencyAnalysis plaintText = 
                new frequencyAnalysis(decryptedText,1,englishChars);
        plaintText.print(false);
        
        //Step 4: print out the final decrypted message and fine-tune the key
        System.out.println("123456789XJQ");
        for (int i = 0; i < decryptedText.length(); i++) {
            System.out.print(decryptedText.charAt(i));
            if ((i+1)%12 == 0)
                System.out.println();
        }
        System.out.println();
        
    }

    /**
     * takes in 8 characters and shifts all of them 
     * until best match to "ETNORIAS".
     * @param caaft The caaft string.
     * @return The string that provide the best match as a key
     */
    public static String match8(String caaft) {
        String best8 = "etnorias";
        String top8 = caaft.substring(0, 8);
        int[] matchScore = new int[26];
        for (int i = 0; i < 26; i++) {
            String key = String.valueOf(englishChars[i]);
            String encoded = vigenereCipher.decode(englishChars,top8,key);
            matchScore[i] = matchUp(encoded,best8);
        }
        
        int index = 0;
        int max = 0;
        for (int i = 0; i < 26; i++) {
            if (matchScore[i] > max) {
                index = i;
                max = matchScore[i];
            }
        }
        return String.valueOf(englishChars[index]);
    }
    
    /**
     * Compares 2 Strings and see how many characters match.
     * e.g. abcde and dexyz has 2 matches.
     * @param a a String.
     * @param b another String. Assumes same length as a.
     * @return number of matches.
     */
    private static int matchUp(String a, String b) {
        int score = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.contains(b.subSequence(i, i+1))) {
                score++;
            }
        }
        return score;
    }

}
