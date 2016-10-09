package xor;

public class main {
    public static void main(String[] args) {
        for (int i = 1; i < 16; i++) {
            for (int j = 1; j < 16; j++) {
                for (int k = 1; k < 16; k++) {
                    for (int l = 1; l < 16; l++) {
                        if (i!=j&&i!=k&&i!=l&&j!=k&&j!=l&&k!=l) {
                            System.out.println(i+","+j+","+k+","+l);
                        }
                    }
                }
            }
        }
    }
    
    public static void whateverthatwas() {
        String[] a = {"00","01","10","11"};
        String[] rules = new String[256];
        
        int n = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        rules[n++] = a[i]+a[j]+a[k]+a[l];
                    }
                }
            }
        }

        for (int i = 0; i < 256; i++) {
            test(rules[i]);
        }
    }
        

    private static void test(String rule) {
        //System.out.print("Testing rule " + rule);
        String[] a = {"00","01","10","11"};
        String[] b = {"00","01","10","11"};
        String[] axb = new String[16];
        String[] enca = new String[16];
        String[] encb = new String[16];
        String[] encaxb = new String[16];
        String[] encaxencb = new String[16];
        int n = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                axb[n] = xor(a[i],b[j]);
                enca[n] = enc(a[i],rule);
                encb[n] = enc(b[j],rule);
                encaxb[n] = enc(axb[n],rule);
                encaxencb[n] = xor(enca[n],encb[n]);
                n++;
            }
        }
        boolean match = true;
        for (int i = 0; i < 16; i++) {
            if (!encaxb[i].equals(encaxencb[i])) {
                match = false;                
                //break;
                return;
            }
        }
        if (match) {
            System.out.println("Match on rule " + rule);
        } else {
            System.out.println("No Match on rule " + rule);
            System.out.println("AA BB AB EA EB AB ++");
            n = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(a[i]+" ");
                    System.out.print(b[j]+" ");
                    System.out.print(axb[n]+" ");
                    System.out.print(enca[n]+" ");
                    System.out.print(encb[n]+" ");
                    System.out.print(encaxb[n]+" ");
                    System.out.println(encaxencb[n]+" ");
                    n++;
                }
            }
        }
    }

    private static String xor(String a, String b) {
        String ans = "";
        if (a.charAt(0)== b.charAt(0)) {
            ans += '0';
        } else {
            ans += '1';
        }
        if (a.charAt(1)== b.charAt(1)) {
            ans += '0';
        } else {
            ans += '1';
        }
        return ans;
    }

    private static String enc(String s, String rule) {
        if (s.equals("00")) {
            return rule.substring(0, 2);
        } else if (s.equals("01")) {
            return rule.substring(2, 4);
        } else if (s.equals("10")) {
            return rule.substring(4, 6);
        } else if (s.equals("11")) {
            return rule.substring(6, 8);
        }
        return null;
    }
}
