import domain.Pair;
import domain.Scanner;
import domain.ProgramInternalForm;
import domain.SymbolTable;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]){
        SymbolTable symbolTable=new SymbolTable();
        ProgramInternalForm programInternalForm=new ProgramInternalForm();
        Scanner Scanner=new Scanner();
        StringBuilder errorMessage=new StringBuilder();
        String fileName = "C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\p1err.txt";
        String line;
        int count=0;
        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            BufferedWriter writerSymbolTable = new BufferedWriter(new FileWriter("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\SymbolTable.out"));
            BufferedWriter writerPIF = new BufferedWriter(new FileWriter("C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\PIF.out"));

            
            while((line = bufferedReader.readLine()) != null) {
                count++;
                ArrayList<String> tokens=Scanner.tokenize(line.strip());
                for (String token: tokens){
                    if(Scanner.isOperator(token) || (token.length()==1 && Scanner.isSeparator(token.charAt(0))) || Scanner.isReservedWord(token)) {
                        programInternalForm.add(token,new Pair(0,0));
                    }
                    else if(Scanner.isConstant(token)){
                        programInternalForm.add("0",symbolTable.pos(token));
                    }
                    else if(Scanner.isIdentifier(token)){
                        programInternalForm.add("1",symbolTable.pos(token));
                    }
                    else{
                        errorMessage.append("Error at line: "+count+" because of token "+ token+ "\n");
                    }

                }
            }
            writerSymbolTable.write(symbolTable.toString());
            writerPIF.write(programInternalForm.toString());
            writerPIF.close();
            writerSymbolTable.close();
            if(errorMessage.length()!=0) {
                System.out.println(errorMessage);
            }
            else{
                System.out.println("No error yay !");
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }

        
    }

}
