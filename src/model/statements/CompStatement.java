package model.statements;

import containers.MyIStack;
import containers.MyStack;
import model.PrgState;
import model.statements.Statement;

public class CompStatement implements Statement {

    Statement first;
    Statement second;

    public CompStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIStack<Statement> exeStack = ps.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        return null;
    }

    public Statement getFirst() {
        return first;
    }

    public Statement getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "( " + first + " ; " + second + " )";
    }
}
