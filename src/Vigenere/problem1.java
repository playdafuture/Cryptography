package Vigenere;

/**
 * Functions and methods used to solve problem 1.
 * This is essentially a test class for 5 & 6 but just happen to solve problem 1.
 * 
 * Note that all files are being shared in the package 
 * for the purpose of function classification. 
 * e.g. frequencyAnalysis for counting and vigenereCipher for character shifting.
 * @author Jinqiu Liu
 * @collaborator Ronnie Yuan
 */
public class problem1 {
    public static String encryptedText = "sqmvwtezknehmqljusenscippgmblcumouforqswulzcrxxtzrxnboaeqxvyfhrpcldaimteqdhbuzrqmpiefgibpfxrsogpmqjdmlpuiatldhibipoztnsdzhqkmjumeubokrgqbxndvcpetdpjtnuumxvdbkijttzfsobwgsikveusljulymsctsmoimgzdrtxseuuicstojwwpcyzhnuzonyaulzzqxszgrpxpvumkpmlermcilfzqavoqkcbulyoimbypvewuwauibnlvdwczearxavendjxspmvewuzzzqkmtzfrhnathxqbemlgdsemhpnezrslrtqmhvyszbnvcjzzblnbeqcsogpmsyafmkcmbtpyaprorzzxdsppdjxsxqcywgtzhwqfoedrccprnvnnjfhqnjyfnxqjdnqijusumkfpcxcwlbcodljmqyzhnvammhcilfrsubxqkcjoogmjjtsunrjcwqsljuoafwkbcwzxvflehljmenxxqfxigcrjyfgmbxpmjtrqtzfxrnpaetnbnqgeefyaciujrtsxxqlerefbjfgicjxq";
    public static char[] englishChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static void main(String[] args) {
        frequencyAnalysis cipherText = new frequencyAnalysis(encryptedText,1,englishChars);
        cipherText.print(false);        
    }

}
