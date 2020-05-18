package main;

public class Arithmetic {

    public static boolean xOR(boolean A, boolean B){

        return A != B;

    }

    public static boolean[] parseStringToBitString(String s){

        char[] chars = s.toCharArray();

        int size = 0;
        for (char c:
             chars) {
            if (c == '0' || c == '1'){
                size += 1;
            }
        }

        boolean[] bitstring = new boolean[size];

        int counter = 0;
        for (char c:
             chars) {

            switch (c){
                case '0':
                    bitstring[counter] = false;
                    counter++;
                    break;

                case '1':
                    bitstring[counter] = true;
                    counter++;
                    break;

                default:
                    break;
            }

        }

        return bitstring;
    }

}
