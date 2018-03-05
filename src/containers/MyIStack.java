package containers;

 public interface MyIStack<T> {
     void push(T elem);
     T pop();
     boolean isEmpty();
     Iterable<T> getAll();
     T top();
}
