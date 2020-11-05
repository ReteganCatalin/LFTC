package FA;


import java.util.Scanner;

public class Main {
    private static FiniteAutomata finiteAutomata=new FiniteAutomata();

    private static void readFiniteAutomata() {
        try {
            finiteAutomata.read("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\FA\\FA.in");
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

    private  static void checkSequence(){
        System.out.println(finiteAutomata.acceptsSequence("000010"));
    }

    private static void displayOptions() {
        System.out.println("1 Display Finite Automata States");
        System.out.println("2 Display Finite Automata Alphabet");
        System.out.println("3 Display Finite Automata Initial State");
        System.out.println("4 Display Finite Automata Final State");
        System.out.println("5 Display Finite Automata Transitions");
        System.out.println("6 Display if DFA");
        System.out.println("7 Check if sequence");
        System.out.println("8 Exit");
    }
    public static void main(String args[]){
        Main.readFiniteAutomata();
        Main.displayOptions();
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        while(true){
            int i=scan.nextInt();
            switch (i){
                case 1:
                    displayStates();
                    break;
                case 2:
                    displayAlphabet();
                    break;
                case 3:
                    displayInitialState();
                    break;
                case 4:
                    displayFinalStates();
                    break;
                case 5:
                    displayTransitions();
                    break;
                case 6:
                    checkDFA();
                    break;
                case 7:
                    checkSequence();
                    break;
                default:
                    System.out.println("Wrong input");
                    displayOptions();

            }
            if(i==8)
                break;
        }
    }
}
