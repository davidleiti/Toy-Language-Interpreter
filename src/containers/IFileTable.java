package containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IFileTable<K,V> {
    V get(K key);
    boolean contains(K key);
    void add(K key,V value);
    void remove(K key);
    Collection<V> getValues();
    Iterable<K> getKeys();
    Map<K, V> getAll();
}
