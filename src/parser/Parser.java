package parser;


import domain.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Pair<String, String>> ClosureLR(String analysis) {
        List<String> tokens = Arrays.stream(analysis.strip()
                .replace("->", " ")
                .split(" "))
                .collect(Collectors.toList());
        List<Pair<String, String>> P = new ArrayList<>();
        P.add(new Pair<>(tokens.get(0), tokens.get(1)));
        int index;
        char nonT;
        int size = 1;

        do {
            size = P.size();
            List<Pair<String, String>> filteredP = new ArrayList<>(P);
            for (Pair<String, String> production : filteredP) {
                index = production.getValue().indexOf('.');
                if (index != -1 && index < production.getValue().length() - 1) {
                    nonT = production.getValue().charAt(index + 1);
                    List<Pair<String, String>> filteredB = grammar.filterP(Character.toString(nonT));
                    for (Pair<String, String> productionB : filteredB) {
                        if (!P.contains(new Pair<>(productionB.getKey(), "." + productionB.getValue()))) {
                            P.add(new Pair<>(productionB.getKey(), "." + productionB.getValue()));
                        }
                    }
                }
            }

        } while (size < P.size());
        return P;
    }


    public List<Pair<String, String>> gotoLR(List<Pair<String, String>> productions, String symbol) {
        List<Pair<String, String>> nestedList = new ArrayList<>();
        productions.stream()
                .filter(item -> item.getValue().contains("." + symbol))
                .map(item -> new Pair<>(item.getKey(), item.getValue().replace("." + symbol, symbol + ".")))
                .forEach(item -> nestedList.addAll(ClosureLR(item.getKey() + "->" + item.getValue())));
        return nestedList.stream().distinct().collect(Collectors.toList());


    }

    public List<List<Pair<String, String >>> ColCan_LR() {

        List<List<Pair<String, String >>> C = new ArrayList<>();

        C.add(ClosureLR("S'->.S"));

        boolean dirty = false;

        do{
            for (List<Pair<String, String >> state: C) {
                for (String element: Stream.concat(getN().stream(), getE().stream())
                        .collect(Collectors.toList())) {

                    List<Pair<String, String>> goToRes = gotoLR(state, element);

                    if(!goToRes.isEmpty() && !includedForEach(C, goToRes)) {
                        C.add(goToRes);
                        dirty = true;
                    }
                }
            }
        }while (dirty);

        return C;
    }

    private boolean includedForEach(List<List<Pair<String, String >>> C, List<Pair<String, String >> goToRes){
        return C.stream().anyMatch((listOfStates) -> included(listOfStates, goToRes));
    }

    private boolean included(List<Pair<String, String >> C, List<Pair<String, String >> goToRes){
        return C.containsAll(goToRes);
    }
}
