package containers;

import exceptions.ContainerException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private ArrayList<T> elements;

    public MyList() {
        elements = new ArrayList<>();
    }

    @Override
    public boolean contains(T elem) {
        return elements.contains(elem);
    }

    @Override
    public void add(T elem) {
        elements.add(elem);
    }

    @Override
    public T get(int pos)
    {
        if (pos < 0 || pos > elements.size())
            throw new ContainerException("Invalid position!");
        return elements.get(pos);
    }

    @Override
    public void set(int pos, T elem) {
        if (pos < 0 || pos > elements.size())
            throw new ContainerException("Invalid position!");
        elements.set(pos,elem);
    }

    @Override
    public Iterable<T> getIterable() {
        return elements;
    }

    @Override
    public List<T> getAll(){
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

}
