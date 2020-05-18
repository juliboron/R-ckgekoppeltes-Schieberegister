package main;

public class Convert {
    public static void bitstringasString(boolean[] bitstring){
        System.out.println("\n");
        for (boolean bit:
             bitstring) {

            if (bit) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
        }
    }

    public static int boolToInt(boolean state){

        if (state){
            return 1;
        }else{
            return 0;
        }

    }
}
