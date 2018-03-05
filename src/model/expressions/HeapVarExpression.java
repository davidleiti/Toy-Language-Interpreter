package model.expressions;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.IHeap;

public class HeapVarExpression implements Expression {

    private String varName;

    public HeapVarExpression(String varName) {
        this.varName = varName;
    }

    @Override
    public Integer eval(MyIDictionary<String, Integer> symbTable, IHeap<Integer, Integer> heap) {
        if (!symbTable.contains(varName))
            throw new DomainException("Variable not initialzed!");
        int id = symbTable.get(varName);
        if (!heap.contains(id))
            throw new DomainException("Invalid heap address!");
        return heap.get(id);
    }

    @Override
    public String toString() {
        return "rH(" + varName + ")";
    }
}
