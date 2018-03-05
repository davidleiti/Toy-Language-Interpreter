package containers;

import java.util.Collection;
import java.util.Map;

public interface MyIDictionary<Key,Value> {
     void add(Key key, Value value);
     void set(Key key, Value value);
     boolean contains(Key key);
     Value get(Key key);
     Collection<Value> getValues();
     Iterable<Key> getKeys();
     Map<Key, Value> getCollection();
     MyIDictionary<Key,Value> copyDictionary();
}
