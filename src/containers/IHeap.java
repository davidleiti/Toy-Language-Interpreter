package containers;

import javafx.util.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

 public interface IHeap<K,V> {
     void add(K key, V value);
     V get(K key);
     boolean contains(K key);
     HashMap<K,V> getValues();
     Iterable<K> getAll();
     void set(K key, V newValue);
     void setContent(HashMap<K,V> newHeap);

}
