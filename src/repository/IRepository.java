package repository;

import model.PrgState;
import model.statements.Statement;

import java.util.List;

public interface IRepository {
    void loadProgram(Statement statement);
    List<PrgState> getPrograms();
    void setPrograms(List<PrgState> programs);
    void logPrgStateExec(PrgState program);
};
