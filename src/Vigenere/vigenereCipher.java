package Vigenere;

/**
 * Functions used related to character shifting.
 * @author Jinqiu Liu
 * @collaborator Ronnie Yuan
 */
public class vigenereCipher {    
    
    public static String encode(char[] charSet, String plainText, String key) {
        String cryptText = "";
        int l = key.length();
        for (int i = 0; i < plainText.length(); i++) {
            char keyChar = key.charAt(i%l);            
            int positions = -1;
            for (int j = 0; j < 32; j++) {
                if (keyChar == charSet[j]) {
                    positions = j;
                    break;
                }
            }
            if (positions == -1) { return null; }
			//in case char not found in charset            
            cryptText += shift(charSet, plainText.charAt(i), positions);
        }
        return cryptText;
    }
 
    public static String decode(char[] charSet, String cryptText, String key) {
        String plainText = "";
        int l = key.length();
        for (int i = 0; i < cryptText.length(); i++) {
            char keyChar = key.charAt(i%l);            
            int positions = -1;
            for (int j = 0; j < 32; j++) {
                if (keyChar == charSet[j]) {
                    positions = j;
                    break;
                }
            }
            if (positions == -1) {
                return null; //in case char not found in charset
            }
            plainText += shift(charSet, cryptText.charAt(i), (-1) * positions);
        }         
        return plainText;
    }   

    public static char shift(char[] charSet, char c, int pos) {
        for (int i = 0; i < charSet.length; i++) {
            if (charSet[i] == c) {
                return charSet[(i+pos+charSet.length)%charSet.length];
            }
        }
        return '*'; //char not found in set
    }
}
