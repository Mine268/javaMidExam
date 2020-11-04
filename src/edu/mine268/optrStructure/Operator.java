package edu.mine268.optrStructure;

import edu.mine268.exprMgr.ConstSome;
import edu.mine268.numStructure.*;

public class Operator extends Node implements ConstSome {
    // 该运算符的类型
    private OPTR type;

    public OPTR getType() {
        return type;
    }

    public char getSymb() {
        return switch (this.type) {
            case PLUS -> '+';
            case MINUS -> '-';
            case MULTIPLY -> '*';
            case DIVIDE -> '/';
            case CONTRAST -> '!';
        };
    }

    /**
     * @author mine268
     * initiate the Operation node
     * @param i the operator in char type
     */
    public Operator(char i) throws WrongOperator {
        switch (i) {
            case '+' : this.type = OPTR.PLUS; break;
            case '-' : this.type = OPTR.MINUS; break;
            case '*' : this.type = OPTR.MULTIPLY; break;
            case '/' : this.type = OPTR.DIVIDE; break;
            case '!' : this.type = OPTR.CONTRAST; break;
            default:
                throw new WrongOperator(i);
        }
    }

    /**
     * @author Mine268
     * Caculate the value based on the operator and values
     * between the operator (in childeren:node[])
     * @return double
     */
    public double calculate() {
        // 如果是取负数运算符（单目运算符）
        if (this.type == OPTR.CONTRAST) {
            // 如果取负数的是一个表达式
            if (this.children[0] instanceof Operator) {
                return -((Operator) this.children[0]).calculate();
            }
            // 如果取负数的是一个数字，变量或者函数
            if (this.children[0] instanceof NumberNode) {
                return -(((NumberNode) this.children[0]).getValue());
            }
        } else { // 如果是双目运算符
            double[] res = new double[2];
            for (int i : new int[]{0, 1}) {
                if (this.children[i] instanceof Operator)
                    res[i] = ((Operator) this.children[i]).calculate();
                else if (this.children[i] instanceof NumberNode)
                    res[i] = ((NumberNode) this.children[i]).getValue();
            }
            switch (this.type) {
                case PLUS:
                    return res[0] + res[1];
                case MINUS:
                    return res[0] - res[1];
                case MULTIPLY:
                    return res[0] * res[1];
                case DIVIDE:
                    // 注意除0错误
                    return res[0] / res[1];
            }
        }
        return Double.NaN;
    }
}
