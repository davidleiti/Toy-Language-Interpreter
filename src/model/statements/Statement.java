package model.statements;

import model.PrgState;

public interface Statement {
    public PrgState execute(PrgState ps);
}
