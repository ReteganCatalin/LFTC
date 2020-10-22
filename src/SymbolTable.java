import java.util.ArrayList;

public class SymbolTable {
    
    // hashList is used to store the array of chains
    private ArrayList<HashNode<String>> hashList;


    //number of nodes in the hashList
    private int startingNodes;

    class HashNode<K> {
        K key;
        // Reference to next node
        HashNode<K> next;
        // Constructor
        public HashNode(K key) {
            this.key = key;
        }
    }

    public SymbolTable() {
        hashList = new ArrayList<>();
        startingNodes = 10;

        // Create empty chains
        for (int i = 0; i < startingNodes; i++)
            hashList.add(null);
    }


    // This implements hash function to find index for a key
    // we use the sum of the ascii value of each character in the key % 256 and % startingNodes
    private int getListIndex(String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum = (sum + key.charAt(i)) % 256;
        }
        int index = sum % startingNodes;
        return index;
    }

    //Gets as a parameter the whole String token(identifier or constant)
    //As the output parameter we will have a Pair which will be either null if we didn't find the token or
    //having the key the index in the table of Nodes and as the value of the Pair the position in which we found the token in that Node list
    private Pair<Integer, Integer> search(String token) {
        // Find head of chain for given key
        int listIndex = getListIndex(token);
        HashNode<String> head = hashList.get(listIndex);
        int pos = 0;
        // Search key in chain
        while (head != null) {
            if (head.key.equals(token))
                return new Pair(listIndex, pos);
            head = head.next;
            pos++;
        }
        // If key not found
        return null;
    }


    //Gets as a parameter the whole String token(identifier or constant)
    //As the output parameter we will have a Pair
    //having the key the index in the table of Nodes and as the value of the Pair the position in which we found the token in that Node list or the position in which we added the token
    public Pair<Integer, Integer> pos(String token) {
        Pair<Integer, Integer> index = search(token);
        if (index == null) {
            index = add(token);
        }
        return index;

    }

    // Gets as a parameter the whole String token(identifier or constant)
    // As the output parameter we will have a Pair:
    // having the key the index in the table of Nodes and as the value of the Pair the position in which we insert in that Node list
    private Pair<Integer, Integer> add(String token) {
        // Find head of chain for given key
        int listIndex = getListIndex(token);
        HashNode<String> head = hashList.get(listIndex);
        int pos = 0;
        if (head != null) {
            while (head.next != null) {
                head = head.next;
                pos++;
            }
            pos++;
            HashNode<String> newNode = new HashNode<>(token);
            head.next = newNode;
            return new Pair(listIndex, pos);
        }
        else{
            HashNode<String> newNode = new HashNode<>(token);
            hashList.set(listIndex,newNode);
            return new Pair(listIndex, 0);
        }

    }
}
