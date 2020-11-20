package parser;

import domain.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Grammar {

    private List<String> N;
    private List<String> E;
    private List<Pair<String, String>> P;

    public List<String> lineSplitter(String line) {
        return Arrays.stream(line.strip().split(" ")).skip(2L).collect(Collectors.toList());
    }

    public Grammar() {
        N = new ArrayList<>();
        E = new ArrayList<>();
        P = new ArrayList<>();
    }

    public List<String> getN() {
        return N;
    }

    public List<String> getE() {
        return E;
    }

    public List<Pair<String, String>> getP() {
        return P;
    }

    public List<Pair<String, String>> filterP(String nonTerminal){
        return P.stream().filter(pair-> pair.getKey().equals(nonTerminal)).collect(Collectors.toList());
    }

    public void readGrammar(String fileName) throws Exception {
        try (FileReader fileReader = new FileReader(fileName)) {

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            N = lineSplitter(line);
            line = bufferedReader.readLine();
            E = lineSplitter(line);
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                List<String> tokens = Arrays.stream(line.strip()
                        .replace("->", " ")
                        .split(" "))
                        .collect(Collectors.toList());
                if (N.contains(tokens.get(0))) {
                    Pair<String,String>  pair= new Pair( tokens.get(0), tokens.get(1));
                    P.add(pair);
                    line = bufferedReader.readLine();
                } else {
                    throw new Exception("Nonterminal doesn't exist");
                }
            }
        }
    }
}
