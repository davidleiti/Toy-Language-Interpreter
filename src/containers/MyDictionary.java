package containers;

import exceptions.ContainerException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDictionary<Key,Value> implements MyIDictionary<Key,Value> {
    private HashMap<Key,Value> elements;

    public MyDictionary(){
        elements = new HashMap<>();
    }

    @Override
    public Value get(Key k) {
        if (!contains(k))
            throw new ContainerException("Element not found!");
        return elements.get(k);
    }

    @Override
    public boolean contains(Key key) {
        return elements.containsKey(key);
    }

    @Override
    public void add(Key key, Value value) {
        if (contains(key))
            throw new ContainerException("Given key already exists");
        elements.put(key,value);
    }

    @Override
    public void set(Key key, Value value) {
        if (!contains(key))
            throw new ContainerException("Key not found!");
        elements.replace(key,value);
    }

    @Override
    public Collection<Value> getValues() {
        return elements.values();
    }

    @Override
    public Iterable<Key> getKeys() {
        return elements.keySet();
    }

    @Override
    public Map<Key, Value> getCollection(){
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public MyIDictionary<Key, Value> copyDictionary() {
        MyIDictionary<Key, Value> res = new MyDictionary<>();
        elements.entrySet().forEach(e -> res.add(e.getKey(),e.getValue()));
        return res;
    }
}
