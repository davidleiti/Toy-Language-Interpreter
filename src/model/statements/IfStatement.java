package model.statements;

import containers.MyIDictionary;
import containers.MyIStack;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

public class    IfStatement implements Statement {

    private Expression expr;
    private Statement thenStmt;
    private Statement elseStmt;

    public IfStatement(Expression expr, Statement thenStmt, Statement elseStmt){
        this.expr = expr;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String,Integer> symbTable = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        MyIStack<Statement> exeStack = ps.getExeStack();
        if (expr.eval(symbTable, heap) != 0)
            exeStack.push(thenStmt);
        else
            exeStack.push(elseStmt);
        return null;
    }

    @Override
    public String toString() {
        return "if ( " + expr + " ) then ( " + thenStmt + " ) else ( " + elseStmt + " );";
    }
}
