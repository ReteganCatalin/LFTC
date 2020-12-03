package domain;

import java.util.LinkedList;

public class ProgramInternalForm {
    private LinkedList<Pair<String,Pair<Integer,Integer>>> pif;

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("PIF: (");
        int i=0;
        for(Pair<String,Pair<Integer,Integer>> pifElem: pif){
            sb.append(pifElem);
            sb.append("\n");
        }
        sb.append(")");
        return sb.toString();
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
