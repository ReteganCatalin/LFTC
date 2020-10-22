package domain;

import java.util.ArrayList;

public class Scanner {
    Tokens tokens;
    public Scanner() {
        tokens=new Tokens();
    }

    private Boolean isPartOfOperator(char character){
        String characterButString=Character.toString(character);
        for(String operator:tokens.getOperators()){
            if (operator.contains(characterButString)){
                return true;
            }
        }
        return false;
    }

    public Boolean isOperator(String token){
        for(String operator:tokens.getOperators()){
            if (operator.equals(token)){
                return true;
            }
        }
        return false;
    }

    public Boolean isReservedWord(String token){
        for(String reservedWord:tokens.getReservedWords()){
            if (reservedWord.equals(token)){
                return true;
            }
        }
        return false;
    }

    public Boolean isSeparator(Character character){
        for(String separator:tokens.getSeparators()){
            if (separator.charAt(0)==character){
                return true;
            }
        }
        return false;
    }

    private Pair<String,Integer> getOperatorToken(String line,Integer currentIndex){
         String token=" ";
         Integer start=currentIndex;
         while(currentIndex < line.length() && isPartOfOperator(line.charAt(currentIndex))){
             token+=line.charAt(currentIndex);
             currentIndex+=1;
         }
         if(isOperator(token)){
             return new Pair(token,currentIndex);
         }
         else{
             return new Pair(token,start);
         }
    }

    public ArrayList<String> tokenize(String line){

        String token="";
        Integer index=0;
        ArrayList<String> tokenList=new ArrayList<>();
        while(index<line.length()){

            if(isPartOfOperator(line.charAt(index))){
                Pair<String,Integer> operator;
                operator=this.getOperatorToken(line,index);
                if (!operator.key.isEmpty()) {
                    index=operator.value;
                    tokenList.add(operator.key);
                }
                token="";

            }

            else if(line.charAt(index) == '\n'){
                if(!token.isEmpty()){
                    tokenList.add(token);
                }
                index++;
                token="";
            }

            else if(isSeparator(line.charAt(index))){
                if(!token.isEmpty()){
                    tokenList.add(token);
                }
                index++;
                tokenList.add(Character.toString(line.charAt(index)));
                token="";
            }
            token+=line.charAt(index);
            index++;
        }
        if(token.isEmpty()){
            tokenList.add(token);
        }
        return tokenList;

    }

    public Boolean isIdentifier(String token) {
        return token.matches("^[a-zA-Z][a-zA-Z0-9]{0,255}");
    }

    public Boolean isConstant(String token){
        return token.matches("^[0|([+-])[1-9][0-9]*|\"[a-zA-Z0-9]*\"|true|false|'[0-9a-zA-Z]']$");
    }
}
