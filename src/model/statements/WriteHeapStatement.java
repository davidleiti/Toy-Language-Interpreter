package model.statements;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

public class WriteHeapStatement implements Statement {

    private String varName;
    private Expression expr;

    public WriteHeapStatement(String varName, Expression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> st = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        if (!st.contains(varName))
            throw new DomainException("Variable not initialized!");
        int id = st.get(varName);
        int newValue = expr.eval(st,heap);
        if (!heap.contains(id))
            throw new DomainException("Not a valid heap address!");
        heap.set(id,newValue);
        return null;
    }

    @Override
    public String toString() {
        return "writeHeap(" + varName + ", " + expr + ")";
    }
}
