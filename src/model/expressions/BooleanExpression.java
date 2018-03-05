package model.expressions;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.IHeap;

public class BooleanExpression implements Expression {

    private Expression e1,e2;
    private String operator;

    public BooleanExpression(Expression expr1, Expression expr2, String operator) {
        this.e1 = expr1;
        this.e2 = expr2;
        this.operator = operator;
    }

    @Override
    public Integer eval(MyIDictionary<String, Integer> symbTable, IHeap<Integer, Integer> heap) {
        int val1 = e1.eval(symbTable, heap);
        int val2 = e2.eval(symbTable, heap);
        switch (operator) {
            case "==": {
                return (val1 == val2) ? 1 : 0;
            }
            case "!=": {
                return (val1 != val2) ? 1 : 0;
            }
            case "<": {
                return (val1 < val2) ? 1 : 0;
            }
            case ">": {
                return (val1 > val2) ? 1 : 0;
            }
            case "<=": {
                return (val1 <= val2) ? 1 : 0;
            }
            case ">=": {
                return (val1 != val2) ? 1 : 0;
            }
            default:
                throw new DomainException("Not a valid boolean operator: " + operator);
        }
    }

    @Override
    public String toString() {
        return e1 + " " + operator + " " + e2;
    }
}
