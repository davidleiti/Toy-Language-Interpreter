package containers;

import exceptions.DomainException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileTable<K,V> implements IFileTable<K,V> {

    private HashMap<K,V> table;

    public FileTable(){
        table = new HashMap<>();
    }

    @Override
    public V get(K key) throws DomainException{
        return table.get(key);
    }

    @Override
    public boolean contains(K key) {
        return table.containsKey(key);
    }

    @Override
    public void add(K key, V value) throws DomainException {
        table.put(key,value);
    }

    @Override
    public void remove(K key) throws DomainException {
        table.remove(key);
    }

    @Override
    public Collection<V> getValues() {
        return table.values();
    }

    @Override
    public Iterable<K> getKeys() {
        return table.keySet();
    }

    @Override
    public Map<K, V> getAll(){
        return table;
    }

    @Override
    public String toString() {
        return table.toString();
    }
}
