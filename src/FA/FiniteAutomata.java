package FA;

import domain.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FiniteAutomata {
    private List<String> Q;
    private List<String> E;
    private HashMap<Pair<String, String>, List<String>> D;
    private String q0;
    private List<String> F;

    public List<String> getQ() {
        return Q;
    }

    public List<String> getE() {
        return E;
    }

    public HashMap<Pair<String, String>, List<String>> getD() {
        return D;
    }

    public String getQ0() {
        return q0;
    }

    public List<String> getF() {
        return F;
    }

    public FiniteAutomata() {
        Q = new ArrayList<>();
        E = new ArrayList<>();
        D = new HashMap<>();
        F = new ArrayList<>();
    }

    public List<String> lineSplitter(String line) {
        return Arrays.stream(line.strip().split(" ")).skip(2L).collect(Collectors.toList());
    }

    private boolean keyExists(Pair<String,String> k){
        Set<Pair<String,String>> allStateTransitions=D.keySet();
        return allStateTransitions.stream().anyMatch(transition->transition.newEquality(k.getKey(),k.getValue()));

    }

    public void read(String fileName) throws Exception {
        FileReader fileReader =
                new FileReader(fileName);
        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        Q = lineSplitter(line);
        line = bufferedReader.readLine();
        E = lineSplitter(line);
        line = bufferedReader.readLine();
        q0 = lineSplitter(line).get(0);
        if(!Q.contains(q0)){
            throw new Exception("Initial state not a state!");
        }
        line = bufferedReader.readLine();
        F = lineSplitter(line);
        for(String finalState:F){
            if(!Q.contains(finalState)){
                throw new Exception("Final state not a state!");
            }
        }
        bufferedReader.readLine();
        line = bufferedReader.readLine();
        while (line!=null) {
            List<String> tokens = Arrays.stream(line.strip().replace("=", "").replace(",", "").split(" ")).collect(Collectors.toList());
            Pair<String, String> transition = new Pair(tokens.get(0), tokens.get(1));
            if(keyExists(transition)){
                D.get(transition).add(tokens.get(3));
            }
            else{
                D.put(transition,new ArrayList<>());
                D.get(transition).add(tokens.get(3));

            }
            line = bufferedReader.readLine();
        }
    }

    public boolean isDFA(){
        Set<Pair<String,String>> allStateTransitions=D.keySet();
        return allStateTransitions.stream().noneMatch(transition->D.get(transition).size()>1);
    }
}
