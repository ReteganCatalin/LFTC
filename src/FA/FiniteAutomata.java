package FA;

import domain.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
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

    public List<Pair<String, String>> getAllTransition(String nextState, String how) {
        Set<Pair<String, String>> allStateTransitions = D.keySet();
        return allStateTransitions.stream().filter(transition -> transition.getKey().equals(nextState) && D.get(transition).contains(how)).collect(Collectors.toList());
    }

    public boolean acceptsSequence(String check) {
        if (isDFA()) {
            List<Pair<String, String>> nextMoves;
            boolean found = false;
            nextMoves = getAllTransition(q0, check.substring(0, 1));
            Queue<Pair<String, String>> queue = new LinkedList<>(nextMoves);
            int position = 1;
            if(!queue.isEmpty() && position==check.length()){
                return true;
            }
            while (!queue.isEmpty() && position != check.length()) {
                Pair<String, String> transition = queue.remove();
                nextMoves = getAllTransition(transition.getValue(), check.substring(position, position + 1));
                position += 1;
                queue.addAll(nextMoves);
                if (position == check.length() && !nextMoves.isEmpty() && F.contains(nextMoves.get(0).getValue())) {
                    found = true;
                }
            }
            return found;
        }
        return false;

    }

    private boolean keyExists(Pair<String, String> k) {
        Set<Pair<String, String>> allStateTransitions = D.keySet();
        return allStateTransitions.stream().anyMatch(transition -> transition.newEquality(k.getKey(), k.getValue()));

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
        if (!Q.contains(q0)) {
            throw new Exception("Initial state not a state!");
        }
        line = bufferedReader.readLine();
        F = lineSplitter(line);
        for (String finalState : F) {
            if (!Q.contains(finalState)) {
                throw new Exception("Final state not a state!");
            }
        }
        bufferedReader.readLine();
        line = bufferedReader.readLine();
        while (line != null) {
            List<String> tokens = Arrays.stream(line.strip().replace("=", "").replace(",", "").split(" ")).collect(Collectors.toList());
            Pair<String, String> transition = new Pair(tokens.get(0), tokens.get(1));
            if (keyExists(transition)) {
                D.get(transition).add(tokens.get(3));
            } else {
                D.put(transition, new ArrayList<>());
                D.get(transition).add(tokens.get(3));

            }
            line = bufferedReader.readLine();
        }
    }

    public boolean isDFA() {
        Set<Pair<String, String>> allStateTransitions = D.keySet();
        for (Pair<String, String> transition : allStateTransitions) {
            List<String> possibilities = D.get(transition);
            for (String pair : Q) {
                if (!pair.equals(transition.getValue())) {
                    List<String> values = D.get(new Pair(transition.getKey(), pair));
                    if (values != null)
                        if(values.stream().anyMatch(value -> possibilities.stream().anyMatch(pos -> pos.equals(value)))) {
                             return false;
                    }
                }
            }
        }
        return true;
    }
}
