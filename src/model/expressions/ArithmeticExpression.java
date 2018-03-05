package model.expressions;

import containers.MyIDictionary;
import exceptions.DomainException;
import containers.IHeap;

public class ArithmeticExpression implements Expression {

    private char operator;
    private Expression left,right;

    public ArithmeticExpression(char operator, Expression leftExpr, Expression rightExpr){
        this.operator = operator;
        left = leftExpr;
        right = rightExpr;
    }

    @Override
    public Integer eval(MyIDictionary<String, Integer> symbTable, IHeap<Integer, Integer> heap) {
        int leftNr = left.eval(symbTable, heap);
        int rightNr = right.eval(symbTable, heap);
        switch (operator) {
            case '+':
                return leftNr + rightNr;
            case '-':
                return leftNr - rightNr;
            case '*':
                return leftNr * rightNr;
            case '/': {
                if (rightNr == 0)
                    throw new DomainException("Domain exception: Division by 0 in " + toString() + "!");
                return leftNr / rightNr;
            }
            default:
                throw new DomainException("Domain exception: Undefined operator in " + toString() + "!");
        }
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}
