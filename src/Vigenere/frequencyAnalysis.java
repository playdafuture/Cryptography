package Vigenere;

/**
 * Functions used related to frequency analysis
 * @author Jinqiu Liu
 * @collaborator Ronnie Yuan
 */
public class frequencyAnalysis {
    /**
     * A chart object is a 2 row array to keep track of all characters 
     * and their corresponding frequency.
     * Note that the frequency analysis often would utilize an array of charts
     * to keep track of every K'th position, depending on the key's length.
     */
    class chart {
        char[] charArray;
        double[] freqArray;
        int size;
        boolean sorted = false;
        
        public chart(int initSize) {
            size = initSize;
            charArray = new char[size];
            freqArray = new double[size];
        }

        public void set(int index, char c, double f) {
            charArray[index] = c;
            freqArray[index] = f;
        }
        
        public void printChart() {
            if (!sorted) {
                sort();
            }
            for (int i = 0; i < size; i++) {
                System.out.print(charArray[i] + " ");
                System.out.print(freqArray[i]*100 + "%");
                System.out.println();
            }
        }
        
        /**
         * Bubble sort that keeps the two arrays aligned.
         */
        public void sort() {
            boolean cleanPass;
            do {
                cleanPass = true;
                for (int i = 0; i < size-1; i++) {
                    if (freqArray[i+1] > freqArray[i]) {
                        swap(i,i+1);
                        cleanPass = false;
                    }
                }
            } while (!cleanPass);
            sorted = true;
        }
        
        /**
         * Counts the number of 0 frequencies at the end of the array.
         * @return the number of 0s.
         */
        public int zero() {
            int ans = 0;
            for (int i = size-1; i>=0; i--) {
                if (freqArray[i]==0) {
                    ans++;
                } else {
                    break;
                }
            }
            return ans;
        }
        
        private void swap(int i, int j) {
            char tempC = charArray[i];
            double tempF = freqArray[i];
            charArray[i] = charArray[j];
            freqArray[i] = freqArray[j];
            charArray[j] = tempC;
            freqArray[j] = tempF;
        }
    }

    char[] charSet;
    String text;
    int interval;
    chart[] chartArray;
        
    /**
     * Constructs the frequency analysis object.
     * @param s The string to analyze
     * @param inter The interval, this should be 1 or (3 to 12)
     * @param stdSet The standard character set for the language.
     * For English, it would be {'a','b','c',...,'z'}
     */
    public frequencyAnalysis(String s, int inter, char[] stdSet) {
        text = s;
        interval = inter;
        charSet = stdSet;
        if (interval > 1) {
            chartArray = new chart[interval + 1];
            for (int i = 0; i < interval; i++) {
                //fill in chart 0 to chart interval - 1 with data
                chartArray[i] = new chart(charSet.length);                
                fill(chartArray[i], i);
            }
            //calculate the overall data in chartArray[interval]
            chartArray[interval] = new chart(charSet.length);
            for (int j = 0; j < charSet.length; j++) {
                //for each character, calculate the average
                double val = 0;
                for (int i = 0; i < interval; i++) {
                    val += chartArray[i].freqArray[j];
                }
                val /= interval;
                chartArray[interval].charArray[j] = '*';
                chartArray[interval].freqArray[j] = val;
            }
        } else {
            chartArray = new chart[1];
            chartArray[0] = new chart(charSet.length);
            for (int j = 0; j < charSet.length; j++) {
                chartArray[0].charArray[j] = charSet[j];
            }
            fill(chartArray[0], 0);
        }
    }
    
    public void print(boolean detail) {
        if (interval == 1) {
            //no choice about details, just print
            chartArray[0].printChart();
        } else {
            System.out.println("Overall chart********");
            chartArray[interval].printChart();
            if (detail) {                
                for (int i = 0; i < interval; i++) {
                    System.out.println("Chart for pos " + i + "******");
                    chartArray[i].printChart();
                }
            }
        }
    }
    
    /**
     * Concatenate all the characters from most to least frequent into a String.
     * @param pos Specifies the interval position: 0 to keyLength-1
     * @return The concatenated string.
     */
    public String fcString(int pos) {
        String s = "";
        for (int i = 0; i < charSet.length; i++) {
            s += chartArray[pos].charArray[i];
        }
        return s;
    }
    
    public int zero(int pos) {
        return chartArray[pos].zero();
    }
    
    private void fill(chart c, int pos) {
        //initialize charArray to the standard charSet
        for (int j = 0; j < charSet.length; j++) {
            c.charArray[j] = charSet[j];
            c.freqArray[j] = 0;
        }
        //go thru each char in the text and put them in the corresponding slot
        for (int k = 0; k < text.length(); k++) {
            if (k%interval == pos) {
                for (int i = 0; i < c.size; i++) {
                    if (text.charAt(k) == c.charArray[i]) {
                        c.freqArray[i]++;
                        break;
                    }
                }
            }
        }
        //freq array now represents raw count, it needs to represent frequency
        for (int i = 0; i < c.size; i++) {
            c.freqArray[i] = c.freqArray[i]/text.length();
        }
        c.sort();
    }

}
