package parser;


import domain.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private final Grammar grammar;

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
        List<Pair<String, String>> P=new ArrayList<>();
        P.add(new Pair<>(tokens.get(0),tokens.get(1)));
        int index;
        char nonT;
        int size=1;

        do {
            size= P.size();
            List<Pair<String,String>> filteredP= new ArrayList<>(P);
            for (Pair<String, String> production : filteredP) {
                index = production.getValue().indexOf('.');
                if (index != -1 && index<production.getValue().length()-1) {
                    nonT = production.getValue().charAt(index + 1);
                    List<Pair<String, String>> filteredB = grammar.filterP(Character.toString(nonT));
                    for (Pair<String, String> productionB : filteredB) {
                        if(!P.contains(new Pair<>(productionB.getKey(),"."+productionB.getValue()))) {
                            P.add(new Pair<>(productionB.getKey(), "." + productionB.getValue()));
                        }
                    }
                }
            }

        }while(size<P.size());
        return P;
    }


    public List<Pair<String,String>> gotoLR(List<Pair<String,String>> productions, String symbol){
        List<Pair<String,String>> nestedList=new ArrayList<>();
        productions.stream()
                .filter(item->item.getValue().contains("."+symbol))
                .map(item->new Pair<>(item.getKey(),item.getValue().replace("."+symbol,symbol+".")))
                .forEach(item->nestedList.addAll(ClosureLR(item.getKey()+"->"+item.getValue())));
        return nestedList.stream().distinct().collect(Collectors.toList());


    }



}
