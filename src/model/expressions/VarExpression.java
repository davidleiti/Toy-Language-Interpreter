package model.expressions;

import containers.MyIDictionary;
import exceptions.ContainerException;
import exceptions.DomainException;
import containers.IHeap;

public class VarExpression implements Expression {

    private String varName;

    public VarExpression(String varName){
        this.varName = varName;
    }

    @Override
    public Integer eval(MyIDictionary<String, Integer> symbTable, IHeap<Integer, Integer> heap) {
        try{
            return symbTable.get(varName);
        }
        catch(ContainerException ce){
            throw(new DomainException("Domain exception: Undefined variable: " + varName));
        }
    }

    @Override
    public String toString() {
        return varName;
    }
}
