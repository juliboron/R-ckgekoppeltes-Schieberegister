package main;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here

        boolean repeat = true;
        Scanner scanner = new Scanner(System.in);

        while (repeat) {

            System.out.println("[Schieberegister:: Menu]");
            System.out.println("AsTable                     (1)");
            System.out.println("AsLaTeX                     (2)");
            System.out.println("Exit                        (0)");

            int operation = scanner.nextInt();

            String bitstring = "";
            boolean[] input = null;
            boolean[] g = null;


            switch (operation){
                case 0:
                    repeat = false;
                    break;

                case 1:

                    System.out.println("Gebe den Zähler an");
                    Scanner scanner1 = new Scanner(System.in);
                    bitstring = scanner1.next();

                    System.out.println("Gib die Polynomstruktur an");
                    g = Arithmetic.parseStringToBitString(scanner1.next());
                    input = Arithmetic.parseStringToBitString(bitstring);
                    asTable(input, g);
                    break;

                case 2:

                    System.out.println("Gebe den Zähler an");
                    Scanner scanner2 = new Scanner(System.in);
                    bitstring = scanner2.next();

                    System.out.println("Gib die Polynomstruktur an");
                    g = Arithmetic.parseStringToBitString(scanner2.next());
                    input = Arithmetic.parseStringToBitString(bitstring);
                    asLaTeX(input, g);
                    break;

            }


        }

        scanner.close();


    }

    public static void asTable(boolean[] input, boolean[] g){
        boolean[] register = new boolean[g.length-1];
        final int lastCycle = input.length;


        int inputDigit = 0;


        //Header
        StringBuilder header = new StringBuilder();

        for (int i = register.length-1; i >= 0 ; i--) {
            if (i == register.length-1){
                header.append("Cycle  ||   ").append("u").append(i);
            } else if (i < register.length-1 && i != 0){
                header.append("  |   ").append("u").append(i);
            } else if (i == 0){
                header.append("  |   ").append("u").append(i).append("   ||  Input");
            }
        }

        /*
        System.out.println("Cycle  ||   u3  |   u2  |  u1   |  u0   ||  Input\n" +
                "=======||=======|=======|=======|=======||========="  );
         */

        System.out.println(header);
        boolean highestRegVal = false;
        for (int cycle = 0; cycle <= lastCycle ; cycle++) {

            //INPUT PARSING
            if (cycle<lastCycle){
                inputDigit = Convert.boolToInt(input[cycle]);
            }


            for (int i = register.length-1; i >= 0 ; i--) {

                if (i == 0 && cycle!= 0){
                    register[i] = Arithmetic.xOR(input[cycle-1], highestRegVal);
                    continue;


                } else if (i == 0){
                    continue;
                }
                if (g[g.length-1 - i]){
                    register[i] = Arithmetic.xOR(register[i-1], highestRegVal);
                } else {
                    register[i] = register[i-1];
                }

            }
            highestRegVal = register[register.length-1];


            //PRINTING

            StringBuilder s = new StringBuilder();

            for (int i = register.length-1; i >= 0; i--) {
                if (i == register.length-1){
                    s.append("  ").append(cycle).append("    ||   ").append(Convert.boolToInt(register[i]));
                }else if(i < register.length-1 && i!= 0){
                    s.append("   |   ").append(Convert.boolToInt(register[i]));
                } else if (i == 0){
                    s.append("   |   ").append(Convert.boolToInt(register[i])).append("  ||  ").append(inputDigit);
                }
            }
            System.out.println(s);
        }

        StringBuilder footer = new StringBuilder();
        for (int i = register.length-1; i >= 0; i--) {
            footer.append(Convert.boolToInt(register[i]));
        }
        System.out.println("\n-------------------------------------------------" +
                "\n                    Der Rest ist:                 "+
                "\n                       "+footer+
                "\n"+
                "\n--------------------------------------------------");
    }



    public static void asLaTeX(boolean[] input, boolean[] g){
        boolean[] register = new boolean[g.length-1];
        final int lastCycle = input.length;


        int inputDigit = 0;


        //Header

        StringBuilder header = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        StringBuilder firstLine = new StringBuilder();


        for (int i = register.length-1; i >= 0; i--) {

            if (i == register.length-1){
                columns.append("{").append("|c||").append("c|");

                firstLine.append("Takt & ").append("   $u^").append(i).append("$   ");
            } else if (i == 0){
                columns.append("c||c|}\n");


                firstLine.append("&   $u^").append(i).append("$    &  Input \\\\\n\n\\hline\n\\hline");
            } else {
                columns.append("c|");

                firstLine.append("&   $u^").append(i).append("$   ");
            }



        }


        header
                .append("\\begin{tabular}")
                .append(columns)
                .append(firstLine);

        System.out.println(header);
        /*
        System.out.println ("\\begin{tabular}{|c||c|c|c|c||c|}\n" +
                            "\\hline\n" +
                            "Takt &  $u^3$   &   $u^2$   &   $u^1$   &   $u^0$   &  Input\\\\" +
                            "\\hline\n"+
                            "\\hline");

         */

        boolean highestRegVal = false;
        for (int cycle = 0; cycle <= lastCycle ; cycle++) {

            //INPUT PARSING
            if (cycle<lastCycle){
                inputDigit = Convert.boolToInt(input[cycle]);
            }


            for (int i = register.length-1; i >= 0 ; i--) {

                if (i == 0 && cycle!= 0){
                    register[i] = Arithmetic.xOR(input[cycle-1], highestRegVal);
                    continue;


                } else if (i == 0){
                    continue;
                }
                if (g[g.length-1 - i]){
                    register[i] = Arithmetic.xOR(register[i-1], highestRegVal);
                } else {
                    register[i] = register[i-1];
                }

            }
            highestRegVal = register[register.length-1];


            /*
            //PRINTING
            if (cycle < lastCycle){
                System.out.println ("  "+ cycle +"    &   " + Convert.boolToInt(register[3]) +"   &   " + Convert.boolToInt(register[2])+"   &   "+ Convert.boolToInt(register[1])+"   &   "+ Convert.boolToInt(register[0])+"   &  "+inputDigit+"\\\\\n" +
                                    "\\hline");
            } else {
                System.out.println ("  "+ cycle +"    &   " + Convert.boolToInt(register[3]) +"   &   " + Convert.boolToInt(register[2])+"   &   "+ Convert.boolToInt(register[1])+"   &   "+ Convert.boolToInt(register[0])+"   &  / \\\\\n" +
                                    "\\hline");
            }

             */

            //PRINTING

            StringBuilder s = new StringBuilder();

            for (int i = register.length-1; i >= 0; i--) {
                if (i == register.length-1){
                    s.append("  ").append(cycle).append("    &    ").append(Convert.boolToInt(register[i]));
                }else if(i < register.length-1 && i!= 0){
                    s.append("   &   ").append(Convert.boolToInt(register[i]));
                } else if (i == 0){
                    s.append("   &   ").append(Convert.boolToInt(register[i])).append("   &   ").append(inputDigit).append("\\\\\n\\hline");
                }
            }
            System.out.println(s);

        }

        System.out.println("\\end{tabular}");

        StringBuilder footer = new StringBuilder();
        for (int i = register.length-1; i >= 0; i--) {
            footer.append(Convert.boolToInt(register[i]));
        }

        System.out.println("\n-------------------------------------------------" +
                "\n               Prüfziffern sind:                 "+
                "\n                      "+footer+
                "\n"+
                "\n--------------------------------------------------");
    }

}
