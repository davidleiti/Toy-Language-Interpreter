package containers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Deque<T> stack;

    public MyStack() {
        stack = new ArrayDeque<>();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() {
        if (isEmpty())
            throw new EmptyStackException();
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Iterable<T> getAll() {
        return stack;
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public T top(){
        return stack.getFirst();
    }
}
