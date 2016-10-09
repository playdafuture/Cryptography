package monoalphabetic;

public class permutationGenerator {
    int[] sequence;
    public permutationGenerator() {
        sequence = new int[26];
        for (int i = 0; i < 26; i++) {
            sequence[i] = -1;
        }
        
        for (int i = 0; i < 26; i++) {
            double randd = Math.random()*26;
            randd = randd/1;
            int rand = (int) randd;
            if (contains(rand)) {
                i--;
            } else {
                sequence[i] = rand;
            }
        }
    }
    
    public int[] getSequence() {
        return sequence;
    }

    private boolean contains(int rand) {
        for (int i = 0; i < 26; i++) {
            if (sequence[i] == rand) {
                return true;
            }
        }
        return false;
    }
    
    public void print() {
        for (int i = 0; i < 26; i++) {
            char c = 'a';
            c += sequence[i];
            System.out.println(c);
        }
    }
}
