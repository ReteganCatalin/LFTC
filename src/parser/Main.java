package parser;

import java.util.Scanner;

public class Main {

    public static Parser parser = new Parser();


    private static void readGrammar() {
        try {
            parser.readGrammar("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\parser\\g1.txt");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private static void displayOptions() {
        System.out.println("1 Display Terminals");
        System.out.println("2 Display Non-terminals");
        System.out.println("3 Display Productions");
    }

    public static void main(String[] args) {
        readGrammar();
        displayOptions();
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        while (true) {
            int i = scan.nextInt();
            switch (i) {
                case 1 -> {
                    displayTerminals();
                }
                case 2 -> {
                    displayNonTerminals();
                }
                case 3 -> {
                    displayProductions();
                }
                case 4 ->{
                    System.out.println(parser.ClosureLR("S'->.S"));
                }
            }
            if (i == 0) {
                break;
            }
        }
    }


    private static void displayProductions() {
        System.out.println(parser.getP());
    }


    private static void displayNonTerminals() {
        System.out.println(parser.getE());
    }


    private static void displayTerminals() {
        System.out.println(parser.getN());
    }
}