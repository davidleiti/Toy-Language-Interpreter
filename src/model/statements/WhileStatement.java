package model.statements;

import containers.MyIDictionary;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

public class WhileStatement implements Statement {
    private Expression expr;
    private Statement stmt;

    public WhileStatement(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> symbTable = ps.getSymbols();
        IHeap heap = ps.getHeap();
        int val = expr.eval(symbTable,heap);
        if (val != 0){
            ps.getExeStack().push(this);
            ps.getExeStack().push(stmt);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while (" + expr + ") " + stmt;
    }
}
