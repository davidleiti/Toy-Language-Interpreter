package model.statements;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.FileData;
import containers.IFileTable;
import containers.IHeap;
import model.PrgState;
import model.expressions.Expression;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadStatement implements Statement {

    private final Expression fileVar;
    private final String varName;


    public ReadStatement(Expression fileVar, String varName) {
        this.fileVar = fileVar;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState ps) {
        MyIDictionary<String, Integer> st = ps.getSymbols();
        IHeap<Integer, Integer> heap = ps.getHeap();
        int id = fileVar.eval(st,heap);
        IFileTable<Integer, FileData> ft = ps.getFileTable();
        if (!ft.contains(id))
            throw new DomainException("No such file has been opened yet!");
        BufferedReader buff = ft.get(id).getFileDescriptor();
        try {
            String line = buff.readLine();
            int val = 0;
            if (line != null)
                val = Integer.parseInt(line);
            if (!st.contains(varName))
                st.add(varName,val);
            else
                st.set(varName,val);
        } catch (IOException ioe){
            throw new DomainException(ioe.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Read(" + fileVar + ", " + varName + ")";
    }
}
