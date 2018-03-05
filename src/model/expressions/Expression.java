package model.expressions;
import containers.MyIDictionary;
import containers.IHeap;

public interface Expression {
    public Integer eval(MyIDictionary<String,Integer> symbTable, IHeap<Integer, Integer> heap);
}
