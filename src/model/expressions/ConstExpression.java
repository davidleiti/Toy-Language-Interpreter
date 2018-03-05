package model.expressions;

import containers.MyIDictionary;
import containers.IHeap;

public class ConstExpression implements Expression {

    private int value;

    public ConstExpression(int value){
        this.value = value;
    }

    @Override
    public Integer eval(MyIDictionary<String, Integer> symbTable , IHeap<Integer, Integer> heap) {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConstExpression){
            ConstExpression expr = (ConstExpression)obj;
            return expr.value == value;
        }
        return false;
    }

    @Override
    public String toString() {
        return "" + value;
    }

}
