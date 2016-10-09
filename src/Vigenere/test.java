package Vigenere;

/**
 *
 * @author Future
 */
public class test {
    public static void main(String[] args) {
        String plainText = "mysonisspencerhelovestoeatandfuzzintheeveninghelove"
                + "stoshowerandcantgetoutheiscurrentlytwomonthsoldandcurrentlyc"
                + "riesandidontknowwhyhescrying";
        String key1 = "key";
        String key2 = "board";
        String key3 = "lsybhzyepnfmoab";
        
        String c1 = vigenereCipher.encode(problem5.englishChars, plainText, key1);
        String c12 = vigenereCipher.encode(problem5.englishChars, c1, key2);
        
        System.out.println(c12);
        
        String c2 = vigenereCipher.encode(problem5.englishChars, plainText, key2);
        String c21 = vigenereCipher.encode(problem5.englishChars, c2, key1);
        System.out.println(c21);
        
        String c3 = vigenereCipher.encode(problem5.englishChars, plainText, key3);
        System.out.println(c3);
    }
}
