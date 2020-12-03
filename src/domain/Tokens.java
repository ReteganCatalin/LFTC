package domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Tokens {

    private ArrayList<String> reservedWords;
    private ArrayList<String> separators;
    private ArrayList<String> operators;


    public Tokens() {
        reservedWords=new ArrayList<>();
        separators=new ArrayList<>();
        operators= new ArrayList<>();
        read();
    }

    public void read(){
        String fileName = "C:\\Users\\Catalin\\Desktop\\Faculty\\LFTC\\src\\domain\\Tokens.in";
        String line;
        int i=1;
        try {
            FileReader fileReader =
                    new FileReader(fileName);
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(i < 16) {
                    operators.add(line.strip());
                }
                else if(i <=22){
                    if (line.strip().equals("<space>")){
                        separators.add(" ");
                    }
                    else{
                        separators.add(line.strip());
                    }
                }
                else{
                    reservedWords.add(line.strip());
                }
                i++;
            }
            reservedWords.add("identifier");
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

    public ArrayList<String> getReservedWords() {
        return reservedWords;
    }

    public ArrayList<String> getSeparators() {
        return separators;
    }

    public ArrayList<String> getOperators() {
        return operators;
    }
}
