package domain;

import java.util.Objects;

public class Pair<K,V>{
    K key;

    V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    public boolean newEquality(String pKey,String pValue) {
        if(key.toString().equals(pKey)) {
            if (value.toString().equals(pValue)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return key.equals(pair.key) &&
                value.equals(pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    // Constructor
    public Pair(K key,V value) {
        this.key = key;
        this.value=value;
    }
}