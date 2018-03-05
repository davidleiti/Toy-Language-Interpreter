package model;

import containers.*;
import model.statements.Statement;
import exceptions.StatementExecutionException;
import utils.IDGenerator;

public class PrgState {
    private int id;
    private MyIDictionary<String, Integer> symbols;
    private MyIStack<Statement> exeStack;
    private MyIList<String> out;
    private IFileTable<Integer, FileData> fileTable;
    private IHeap<Integer, Integer> heap;
    private Statement mainStatement;

    public PrgState() {
        this.id = IDGenerator.generateID();
        this.symbols = new MyDictionary<>();
        this.exeStack = new MyStack<>();
        this.out = new MyList<>();
        this.fileTable = new FileTable<>();
        this.heap = new Heap<>();
    }

    public PrgState(MyIDictionary<String, Integer> symbols, MyIStack<Statement> exeStack, MyIList<String> out, IFileTable<Integer, FileData> fileTable, IHeap<Integer, Integer> heap) {
        this.id = IDGenerator.generateID();
        this.symbols = symbols;
        this.exeStack = exeStack;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
    }

    public IHeap<Integer, Integer> getHeap() {
        return heap;
    }

    public IFileTable<Integer, FileData> getFileTable() {
        return fileTable;
    }

    public MyIDictionary<String, Integer> getSymbols() {
        return symbols;
    }

    public MyIStack<Statement> getExeStack() {
        return exeStack;
    }

    public MyIList<String> getOut() {
        return out;
    }

    public int getId() {
        return id;
    }

    public void setMainStatement(Statement mainStatement) {
        this.mainStatement = mainStatement;
        exeStack.push(mainStatement);
    }

    public boolean isCompleted() {
        return exeStack.isEmpty();
    }

    public PrgState oneStepExecution(){
        if (isCompleted())
            throw new StatementExecutionException("Nothing to execute!");
        Statement stmt = exeStack.pop();
        return stmt.execute(this);
    }

    @Override
    public String toString() {
        return  "Program with ID: " + id +
                "\n\tSymbols: " + symbols +
                "\n\tHeap: " + heap +
                "\n\tExecution stack: " + exeStack +
                "\n\tFile table: " + fileTable +
                "\n\tOutput: " + out;
    }
}