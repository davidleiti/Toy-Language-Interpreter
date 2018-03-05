package model.statements;

import containers.MyIDictionary;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;
import utils.IDGenerator;

public class AllocateStatement implements Statement {

    public String varName;
    public Expression expr;

    public AllocateStatement(String varName, Expression expr) {
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> st = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        int val = expr.eval(st,heap);
        int id = IDGenerator.generateID();
        heap.add(id,val);
        if (st.contains(varName))
            st.set(varName,id);
        else
            st.add(varName,id);
        return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expr +")";
    }
}
