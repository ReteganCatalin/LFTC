import domain.Pair;
import domain.Scanner;
import domain.ProgramInternalForm;
import domain.SymbolTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]){
        SymbolTable symbolTable=new SymbolTable();
        ProgramInternalForm programInternalForm=new ProgramInternalForm();
        Scanner Scanner=new Scanner();
        StringBuilder errorMessage=new StringBuilder();
        String fileName = "p1.txt";
        String line;
        int count=0;
        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                count++;
                ArrayList<String> tokens=Scanner.tokenize(line.strip());
                for (String token: tokens){
                    if(Scanner.isOperator(token) || (token.length()==1 && Scanner.isSeparator(token.charAt(0))) || Scanner.isReservedWord(token)) {
                        programInternalForm.add(token,new Pair(0,0));
                    }
                    else if(Scanner.isConstant(token) || Scanner.isIdentifier(token)){
                        programInternalForm.add(token,symbolTable.pos(token));
                    }
                    else{
                        errorMessage.append("Error at line: "+count+" because of token"+ token+ "\n");
                    }

                }
            }
            System.out.println(symbolTable);
            System.out.println(programInternalForm);
            System.out.println(errorMessage);
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
