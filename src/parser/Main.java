package parser;

import domain.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Parser parser = new Parser();


    private static void readGrammar() {
        try {
            parser.readGrammar("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\parser\\g2.txt");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    private static void displayOptions() {
        System.out.println("1 Display Terminals");
        System.out.println("2 Display Non-terminals");
        System.out.println("3 Display Productions");
        System.out.println("4 Choose production to do closure");
        System.out.println("5 Choose symbol to do goto on state(ClosureLR of S'->S)");
        System.out.println("6 Display Canonical Collection");
        System.out.println("7 Display LR table");
        System.out.println("8 Parse word");
        System.out.println("9 Parse with table father sibling");

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
                    System.out.println("Give input:");
                    String value="S'->. S";
                    System.out.println(parser.ClosureLR(value));
                }
                case 5 ->{
                    System.out.println("Give input:");
                    String value=scan.next();
                    displayGoTo(value);

                }
                case 6-> {
                    System.out.println(parser.ColCan_LR());
                }
                case 7->{
                    System.out.println(parser.createLRTable(parser.ColCan_LR()));
                }
                case 8 ->{
                    System.out.println("Give input:");
                    scan.nextLine();
                    String value=scan.nextLine();
                    parseWord(value);
                }
                case 9 ->{
                    System.out.println("Give input:");
                    scan.nextLine();
                    String value=scan.nextLine();
                    createTable(value);
                }
            }
            if (i == 0) {
                break;
            }
        }
    }

    private static void createTable(String word) {
        List<List<Pair<String,String>>>  canCol=parser.ColCan_LR();
        HashMap<Integer, Pair<String, HashMap<String, Integer>>> lrTable=parser.createLRTable(canCol);
        List<String> wordResult=parser.parsingAlg(lrTable,canCol,word);
        if(wordResult==null)
            System.out.println("Error");
        else{
            List<Pair<String, Pair<Integer, Integer>>> table=parser.createTable(wordResult);
            for(int i=0;i<table.size();i++){
                System.out.println(table.get(i));
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

    private static void displayGoTo(String value){
        List<Pair<String,String>> gotoResult=parser.gotoLR(parser.ClosureLR("S'->. S"),value);
        if(gotoResult.isEmpty())
            System.out.println("No results");
        else{
            System.out.println(gotoResult);
        }
    }
    private static void parseWord(String word){
        List<List<Pair<String,String>>>  canCol=parser.ColCan_LR();
        HashMap<Integer, Pair<String, HashMap<String, Integer>>> lrTable=parser.createLRTable(canCol);
        List<String> wordResult=parser.parsingAlg(lrTable,canCol,word);
        if(wordResult==null)
            System.out.println("Error");
        else{
            System.out.println(wordResult);
        }
    }

}