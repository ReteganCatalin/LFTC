package parser;


import domain.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private Grammar grammar;

    public Parser() {
        this.grammar = new Grammar();
    }

    public List<String> getN() {
        return grammar.getN();
    }

    public List<String> getE() {
        return grammar.getE();
    }

    public List<Pair<String, String>> getP() {
        return grammar.getP();
    }

    public void readGrammar(String filename) throws Exception {
        grammar.readGrammar(filename);
    }

    public List<Pair<String,String>> ClosureLR(String analysis){
        List<String> tokens = Arrays.stream(analysis.strip()
                .replace("->", " ")
                .split(" "))
                .collect(Collectors.toList());
        List<String> NonTerminals=new ArrayList<>();
        List<Pair<String, String>> P=new ArrayList<>();
        P.add(new Pair<>(tokens.get(0),tokens.get(1)));
        int index=tokens.get(1).indexOf('.');
        char nonT=tokens.get(1).charAt(index+1);
        List<Pair<String, String>> filteredP = grammar.filterP(Character.toString(nonT));
        for(Pair<String, String> production: filteredP){
            P.add(production);
            index=production.getValue().indexOf('.');
            if(index!=-1){
                nonT=production.getValue().charAt(index+1);
                List<Pair<String, String>> filteredB = grammar.filterP(Character.toString(nonT));
                for(Pair<String, String> productionB: filteredB){
                    P.add(new Pair<>(productionB.getKey(),"."+productionB.getValue()));
                }
            }
        }
        return P;
    }


}
