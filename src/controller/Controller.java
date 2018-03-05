package controller;
import containers.IHeap;
import exceptions.DomainException;
import exceptions.FileException;
import model.PrgState;
import model.expressions.ArithmeticExpression;
import model.expressions.ConstExpression;
import model.expressions.HeapVarExpression;
import model.expressions.VarExpression;
import model.statements.*;
import model.statements.Statement;
import exceptions.StatementExecutionException;
import repository.IRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repo;
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void executeOneStepAll() {
        List<PrgState> programs = removeFinished();
        programs.forEach(prg -> repo.logPrgStateExec(prg));
        List<Callable<PrgState>> callList = programs.stream()
                .map(prg -> (Callable<PrgState>) (() -> prg.oneStepExecution()))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrograms = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (InterruptedException ie){
                            return null;
                        }
                        catch (ExecutionException ee){
                            throw new StatementExecutionException(ee.getMessage());
                        }
                    })
                    .filter(prg -> prg != null)
                    .collect(Collectors.toList());
            programs.addAll(newPrograms);
            if (programs.size() > 0)
                collectGarbage(programs);
            if (programs.size() > 1)
                repo.setPrograms(programs.stream().filter(prg -> !prg.isCompleted()).collect(Collectors.toList()));
        } catch (InterruptedException ie) {
            throw new StatementExecutionException(ie.getMessage());
        }
    }

    private List<PrgState> removeFinished() {
        return repo.getPrograms().stream().filter(e -> !e.isCompleted())
                .collect(Collectors.toList());
    }

    public void wholeExecution() {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programs = removeFinished();
        while (programs.size() > 0) {
            collectGarbage(programs);
            repo.setPrograms(programs);
            executeOneStepAll();
            programs = removeFinished();
        }
        executor.shutdownNow();
        repo.setPrograms(programs);
    }

    private void collectGarbage(List<PrgState> programs){
        IHeap<Integer, Integer> heap = programs.get(0).getHeap();
        HashMap<Integer, Integer> newMap = new HashMap<>();
        final AtomicReference<List<PrgState>> reference = new AtomicReference<>(programs);
        heap.getValues().keySet().stream()
                .filter(el -> hasPointer(reference.get(), el))
                .forEach(el -> newMap.put(el, heap.getValues().get(el)));
        heap.setContent(newMap);
    }

    private void closeFiles(PrgState prg){
        prg.getFileTable().getValues().stream().forEach(fileData -> {
            try {
                fileData.fileDescriptor.close();
            }
            catch (IOException ioe) {
                throw new StatementExecutionException("Unable to close file!");
            }
        });
    }

    private boolean hasPointer(List<PrgState> programs, int heapID){
        return programs.stream().filter(prg -> prg.getSymbols().getValues().contains(heapID)).count() > 0;
    }

    public void loadProgram(Statement mainStatement) {
        repo.loadProgram(mainStatement);
    }

    public List<PrgState> getPrograms() {
        return repo.getPrograms();
    }
}

