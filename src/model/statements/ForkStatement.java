package model.statements;

import containers.*;
import model.PrgState;

public class ForkStatement implements Statement {

    private Statement stmt;

    public ForkStatement(Statement stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIStack<Statement> exeStack = new MyStack<>();
        exeStack.push(stmt);
        MyIDictionary<String, Integer> tableCopy = ps.getSymbols().copyDictionary();
        PrgState res = new PrgState(tableCopy, exeStack, ps.getOut(), ps.getFileTable(), ps.getHeap());
        return res;
    }

    @Override
    public String toString() {
        return "fork(" + stmt + ")";
    }
}
