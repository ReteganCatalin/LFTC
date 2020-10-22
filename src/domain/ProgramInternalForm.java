package domain;

import java.util.LinkedList;

public class ProgramInternalForm {
    private LinkedList<Pair<String,Pair<Integer,Integer>>> pif;

    @Override
    public String toString() {
        return "domain.ProgramInternalForm{" +
                pif +
                '}';
    }

    public ProgramInternalForm() {
        this.pif = new LinkedList<>();
    }

    public void add(String token,Pair<Integer,Integer> pos)
    {
        Pair<String,Pair<Integer,Integer>> newToken=new Pair(token,pos);
        pif.addLast(newToken);
    }
}
