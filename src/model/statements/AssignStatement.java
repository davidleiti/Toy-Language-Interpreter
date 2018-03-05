package model.statements;

import containers.MyIDictionary;
import exceptions.ContainerException;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

public class AssignStatement implements Statement {

    private String varName;
    private Expression expr;

    public AssignStatement(String varName, Expression expr){
        this.varName = varName;
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> symbTable = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        int result = expr.eval(symbTable, heap);
        try{
            symbTable.set(varName,result);
        }
        catch(ContainerException e){
            symbTable.add(varName,result);
        }
        return null;
    }

    @Override
    public String toString() {
        return varName + " = " + expr;
    }
}
