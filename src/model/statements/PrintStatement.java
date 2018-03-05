package model.statements;

import containers.MyIDictionary;
import containers.MyIList;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

public class PrintStatement implements Statement{

    private Expression expr;

    public PrintStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String,Integer> symbTable = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        MyIList<String> output = ps.getOut();
        int result = expr.eval(symbTable, heap);
        output.add(Integer.toString(result));
        return null;
    }

    @Override
    public String toString() {
        return "Print(" + expr + ")";
    }
}
