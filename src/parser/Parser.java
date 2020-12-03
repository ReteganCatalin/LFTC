package parser;


import domain.Pair;

import java.util.*;
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
                .split("->"))
                .collect(Collectors.toList());
        List<Pair<String, String>> P = new ArrayList<>();
        P.add(new Pair<>(tokens.get(0), tokens.get(1)));
        int index;
        String nonT;
        int size = 1;

        do {
            size = P.size();
            List<Pair<String, String>> filteredP = new ArrayList<>(P);
            for (Pair<String, String> production : filteredP) {
                index = findDot(production.getValue().split(" "));
                if (index != -1 && index < production.getValue().split(" ").length - 1) {
                    nonT = production.getValue().split(" ")[index+1];
                    List<Pair<String, String>> filteredB = grammar.filterP(nonT);
                    for (Pair<String, String> productionB : filteredB) {
                        if (!P.contains(new Pair<>(productionB.getKey(), ". " + productionB.getValue()))) {
                            P.add(new Pair<>(productionB.getKey(), ". " + productionB.getValue()));
                        }
                    }
                }
            }

        } while (size < P.size());
        return P;
    }

    private int findDot(String[] s) {
        for(int i=0;i<s.length;i++){
            if(s[i].equals("."))
                return i;
        }
        return -1;
    }


    public List<Pair<String, String>> gotoLR(List<Pair<String, String>> productions, String symbol) {
        List<Pair<String, String>> nestedList = new ArrayList<>();
        productions.stream()
                .filter(item -> item.getValue().contains(". " + symbol))
                .map(item -> new Pair<>(item.getKey(), item.getValue().replace(". " + symbol, symbol + " .")))
                .forEach(item -> nestedList.addAll(ClosureLR(item.getKey() + "->" + item.getValue())));
        return nestedList.stream().distinct().collect(Collectors.toList());


    }

    public List<List<Pair<String, String>>> ColCan_LR() {

        List<List<Pair<String, String>>> C = new ArrayList<>();

        C.add(ClosureLR("S'->. S"));

        boolean dirty;

        do {
            dirty = false;
            List<List<Pair<String, String>>> filteredC = new ArrayList<>(C);
            for (List<Pair<String, String>> state : filteredC) {
                for (String element : Stream.concat(getN().stream(), getE().stream())
                        .collect(Collectors.toList())) {

                    List<Pair<String, String>> goToRes = gotoLR(state, element);

                    if (!goToRes.isEmpty() && !includedForEach(C, goToRes)) {
                        C.add(goToRes);
                        dirty = true;
                    }
                }
            }
        } while (dirty);

        return C;
    }

    private boolean includedForEach(List<List<Pair<String, String>>> C, List<Pair<String, String>> goToRes) {
        return C.stream().anyMatch((listOfStates) -> included(listOfStates, goToRes));
    }

    private boolean included(List<Pair<String, String>> C, List<Pair<String, String>> goToRes) {
        return C.containsAll(goToRes);
    }

    public HashMap<Integer, Pair<String, HashMap<String, Integer>>> createLRTable(List<List<Pair<String, String>>> states) {
        HashMap<Integer, Pair<String, HashMap<String, Integer>>> lrTable = new HashMap<>();

        for (List<Pair<String, String>> state : states) {
            int position=states.indexOf(state);
            if(state.contains(new Pair("S'","S ."))){
                lrTable.put(position,new Pair("acc",new HashMap<String,Integer>()));
            }
            else if(hasReduce(state)!=-1){
                lrTable.put(position,new Pair("reduce "+hasReduce(state),new HashMap<String,Integer>()));
            }
            else {
                lrTable.put(position,new Pair("shift",new HashMap<String,Integer>()));
                for (String element : Stream.concat(getN().stream(), getE().stream())
                        .collect(Collectors.toList())) {

                    List<Pair<String, String>> goToRes = gotoLR(state, element);

                    if (!goToRes.isEmpty()) {
                        lrTable.get(position).getValue().put(element,states.indexOf(goToRes));

                    }
                }
            }
        }
        return lrTable;
    }



    private Integer hasReduce(List<Pair<String, String>> state) {
        for(Pair<String,String> lrItem: state){
            if(lrItem.getValue().split(" ")[lrItem.getValue().split(" ").length-1].equals(".")){
                return grammar.getP().indexOf(new Pair(lrItem.getKey(),lrItem.getValue().substring(0,lrItem.getValue().length()-2)));
            }
        }
        return -1;
    }

    public List<String> parsingAlg(HashMap<Integer, Pair<String, HashMap<String, Integer>>>  lrTable,List<List<Pair<String, String>>> C,  String word) {
        List<Pair<String, String>> state = C.get(0);
        List<String> alpha = new ArrayList<>();
        List<String> beta = new ArrayList<>();
        List<String> phi = new ArrayList<>();
        alpha.add("0");
        String[] parse=word.split(" ");
        for (int i = 0; i <= parse.length-1; i++) {
            beta.add(parse[i]);
        }
        while(true){
            int position=Integer.parseInt(alpha.get(alpha.size()-1));
            if (lrTable.get(position).getKey().equals("shift")) {
                String a = beta.remove(0);
                state = gotoLR(state, a);
                alpha.add(a);
                alpha.add(Integer.toString(C.indexOf(state)));
            } else if (lrTable.get(position).getKey().contains("reduce")) {
                String findReducer=lrTable.get(position).getKey().substring(7);
                int reducer=Integer.parseInt(findReducer);
                Pair<String,String> production=search_prod(reducer);
                for(int i=0;i<2*production.getValue().split(" ").length;i++) {
                    alpha.remove(alpha.size()-1); //?
                }
                state = gotoLR(C.get(Integer.parseInt(alpha.get(alpha.size()-1))), production.getKey());
                alpha.add(production.getKey());
                alpha.add(Integer.toString(C.indexOf(state)));
                phi.add(findReducer);
            }
            else{
                if(lrTable.get(position).getKey().equals("acc")){
                    Collections.reverse(phi);
                    return phi;
                }
                else{
                    return null;
                }

            }
        }
    }

    private Pair<String, String> search_prod(int reducer) {
        return grammar.getP().get(reducer);
    }

    public List<Pair<String, Pair<Integer, Integer>>> createTable(List<String> productions){
        List<Pair<String,Pair<Integer,Integer>>> table=new ArrayList<>();
        table.add(new Pair<>("S",new Pair<>(-1,-1)));
        int k=0;
        for(String production:productions){
            while(!grammar.getN().contains(table.get(k).getKey())){
                k++;
            }
            Pair<String, String> prod=search_prod(Integer.parseInt(production));
            int index=0;
            table.add(new Pair(prod.getValue().split(" ")[index],new Pair(k,-1)));
            index++;
            for(;index<prod.getValue().split(" ").length;index++){
                table.add(new Pair(prod.getValue().split(" ")[index],new Pair(k,k+index)));
            }
            k++;
        }
        return table;
    }
}
