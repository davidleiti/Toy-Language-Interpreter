package model.statements;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.FileData;
import containers.IFileTable;
import model.PrgState;
import utils.IDGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileStatement implements Statement {
    public final String fileVar;
    public final String fileName;

    public OpenFileStatement(String fileVar, String fileName) {
        this.fileVar = fileVar;
        this.fileName = fileName;
    }

    @Override
    public PrgState execute(PrgState ps) {
        if (exists(ps,fileName))
            throw new DomainException("file already open!");
        try {
            BufferedReader fileDesc = new BufferedReader(new FileReader(fileName));
            FileData data = new FileData(fileName, fileDesc);
            int id = IDGenerator.generateID();
            ps.getFileTable().add(id,data);
            MyIDictionary<String,Integer> symbTable = ps.getSymbols();
            if (!symbTable.contains(fileVar))
                symbTable.add(fileVar,id);
            else
                symbTable.set(fileVar,id);
        } catch (IOException ioe){
            throw new DomainException(ioe.getMessage());
        }
        return null;
    }

    private boolean exists(PrgState ps, String fileName){
        IFileTable<Integer, FileData> ft = ps.getFileTable();
        for (FileData d : ft.getValues())
            if (d.getFileName().equals(fileName))
                return true;
        return false;
    }

    @Override
    public String toString() {
        return "Open(" + fileVar + ", " + fileName + ")";
    }
}
