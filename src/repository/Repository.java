package repository;

import exceptions.DomainException;
import model.*;
import model.statements.Statement;
import exceptions.FileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> programs;
    private String logFileName;

    public Repository(PrgState mainProgram,String logFileName){
        programs = new ArrayList<>();
        programs.add(mainProgram);
        this.logFileName = logFileName;
        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFileName)));
            pw.close();
        }
        catch (IOException ioe){
            throw new FileException("Error opening log file!");
        }
    }

    public List<PrgState> getPrograms() {
        return programs;
    }

    public void setPrograms(List<PrgState> programs) {
        this.programs = programs;
    }

    @Override
    public void loadProgram(Statement statement) {
        programs.clear();
        PrgState prgState = new PrgState();
        prgState.setMainStatement(statement);
        programs.add(prgState);
    }

    @Override
    public void logPrgStateExec(PrgState program) {
        try(PrintWriter pw = new PrintWriter(new BufferedWriter( new FileWriter(logFileName,true)))){
            pw.println("Execution stack:");
            for (Statement st : program.getExeStack().getAll()){
                pw.println(st);
            }

            pw.println("Symbol table:");
            for (String var : program.getSymbols().getKeys()){
                pw.println(var + " -> " +program.getSymbols().get(var));
            }

            pw.println("Output:");
            for (String val : program.getOut().getIterable()){
                pw.println(val);
            }

            pw.println("File table:");
            for (Integer id : program.getFileTable().getKeys()){
                pw.println("" + id + " -> " + program.getFileTable().get(id));
            }

            pw.println("Heap:");
            for (Integer id : program.getHeap().getAll()){
                pw.println(id + " -> " + program.getHeap().get(id));
            }

            pw.println();
        } catch (IOException ioe){
            throw new DomainException(ioe.getMessage());
        }
    }
}


