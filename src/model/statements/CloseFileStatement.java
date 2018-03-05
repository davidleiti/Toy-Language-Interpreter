package model.statements;

import containers.FileData;
import containers.IFileTable;
import containers.IHeap;
import containers.MyIDictionary;
import exceptions.DomainException;
import model.*;
import model.expressions.Expression;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements Statement {

    private Expression fileVar;

    public CloseFileStatement(Expression fileVar) {
        this.fileVar = fileVar;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> st = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        int id = fileVar.eval(st, heap);
        IFileTable<Integer, FileData> ft = ps.getFileTable();
        if (!ft.contains(id))
            throw new DomainException("No such file has been opened yet!");
        BufferedReader buff = ft.get(id).getFileDescriptor();
        try{
            buff.close();
            ft.remove(id);
        }
        catch (IOException ioe){
            throw new DomainException(ioe.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Close(" + fileVar + ")";
    }
}
