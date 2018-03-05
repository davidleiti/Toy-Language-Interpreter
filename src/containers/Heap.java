package containers;

import java.util.HashMap;

public class Heap<K,V> implements IHeap<K,V> {

    private HashMap<K,V> elements = new HashMap<>();

    @Override
    public void add(K key, V value) {
        elements.put(key,value);
    }

    @Override
    public V get(K key) {
        return elements.get(key);
    }

    @Override
    public boolean contains(K key) {
        return elements.containsKey(key);
    }

    @Override
    public Iterable<K> getAll() {
        return elements.keySet();
    }

    @Override
    public HashMap<K,V> getValues() {
        return elements;
    }

    @Override
    public void setContent(HashMap<K, V> newHeap) {
        elements = newHeap;
    }

    @Override
    public void set(K key, V newValue) {
        elements.replace(key,newValue);
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
