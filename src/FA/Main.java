package FA;


import java.util.Scanner;

public class Main {
    private static FiniteAutomata finiteAutomata=new FiniteAutomata();
    private static FiniteAutomata identifierFA= new FiniteAutomata();
    private static FiniteAutomata integerFA= new FiniteAutomata();
    private static FiniteAutomata stringFA= new FiniteAutomata();
    private static FiniteAutomata characterFA= new FiniteAutomata();
    private static FiniteAutomata booleanFA= new FiniteAutomata();

    private static void readFiniteAutomata() {
        try {
            finiteAutomata.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\FA.in");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static void readIdentifierFA() {
        try {
            identifierFA.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\Identifiers.txt");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
    private static void readIntegerFA() {
        try {
            integerFA.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\Integer.txt");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static void readStringFA() {
        try {
            stringFA.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\String.txt");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static void readCharacterFA() {
        try {
            characterFA.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\Character.txt");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private static void readBooleanFA() {
        try {
            booleanFA.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\Boolean.txt");
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }


    private static void displayStates() {
        System.out.println(finiteAutomata.getQ());
    }


    private static void displayAlphabet() {
        System.out.println(finiteAutomata.getE());
    }

    private static void displayTransitions(){
        System.out.println(finiteAutomata.getD());
    }

    private static void displayFinalStates(){
        System.out.println(finiteAutomata.getF());
    }

    private static void displayInitialState(){
        System.out.println(finiteAutomata.getQ0());
    }

    private static void checkDFA() {
        System.out.println(finiteAutomata.isDFA());
    }

    private  static void checkSequence(String sequence){
        System.out.println(finiteAutomata.acceptsSequence(sequence));
    }

    private  static void checkIdentifier(String identifier) {
        System.out.println(identifierFA.acceptsSequence(identifier));
    }

    private  static void checkInteger(String integer) {
        System.out.println(integerFA.acceptsSequence(integer));
    }

    private  static void checkString(String string){
        System.out.println(stringFA.acceptsSequence(string));
    }

    private  static void checkCharacter(String character){
        System.out.println(characterFA.acceptsSequence(character));
    }

    private  static void checkBoolean(String bool){
        System.out.println(booleanFA.acceptsSequence(bool));
    }


    private static void displayOptions() {
        System.out.println("1 Display Finite Automata States");
        System.out.println("2 Display Finite Automata Alphabet");
        System.out.println("3 Display Finite Automata Initial State");
        System.out.println("4 Display Finite Automata Final State");
        System.out.println("5 Display Finite Automata Transitions");
        System.out.println("6 Display if DFA");
        System.out.println("7 Check if sequence");
        System.out.println("8 Check if identifier");
        System.out.println("9 Check if integer");
        System.out.println("10 Check if string");
        System.out.println("11 Check if char");
        System.out.println("12 Check if boolean");
        System.out.println("13 Exit");
    }
    public static void main(String[] args){
        Main.readFiniteAutomata();
        Main.readIdentifierFA();
        Main.readIntegerFA();
        Main.readCharacterFA();
        Main.readBooleanFA();
        Main.readStringFA();
        Main.displayOptions();
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        while(true){
            int i=scan.nextInt();
            switch (i){
                case 1: {
                    displayStates();
                    break;
                }
                case 2: {
                    displayAlphabet();
                    break;
                }
                case 3: {
                    displayInitialState();
                    break;
                }
                case 4: {
                    displayFinalStates();
                    break;
                }
                case 5: {
                    displayTransitions();
                    break;
                }
                case 6: {
                    checkDFA();
                    break;
                }
                case 7: {
                    System.out.println("Give input:");
                    String value=scan.next();
                    checkSequence(value);
                    break;
                }
                case 8: {
                    System.out.println("Give input:");
                    String value=scan.next();
                    checkIdentifier(value);
                    break;
                }
                case 9: {
                    System.out.println("Give input:");
                    String value = scan.next();
                    checkInteger(value);
                    break;
                }
                case 10:{
                    System.out.println("Give input:");
                    String value=scan.next();
                    checkString(value);
                    break;
                }
                case 11: {
                    System.out.println("Give input:");
                    String value = scan.next();
                    checkCharacter(value);
                    break;
                }
                case 12: {
                    System.out.println("Give input:");
                    String value = scan.next();
                    checkBoolean(value);
                    break;
                }
                default:
                    System.out.println("Wrong input");
                    displayOptions();

            }
            if(i==13)
                break;
        }
    }
}
