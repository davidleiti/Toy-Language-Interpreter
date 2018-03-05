package containers;

import java.util.ArrayList;
import java.util.List;

public interface MyIList<T> {
     void add(T elem);
     T get(int pos);
     void set(int post, T elem);
     boolean contains(T elem);
     Iterable<T> getIterable();
     List<T> getAll();
}
