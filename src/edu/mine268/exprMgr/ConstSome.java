package edu.mine268.exprMgr;

import java.math.BigDecimal;

/**
 * Interface for some common variables and enumerations.
 * @author mine268
 */
public interface ConstSome {
    // subtrees sum for every node
    public final static int CHILD_SUM = 2;
    // priority for each operator:
    // 1 for superior, 0 for equal, -1 for inferior
    public static final int[][] table = new int[][]{
            //+ - * / % ^ ( ) #
            {0, 0, -1, -1, -1, -1, -1, 1, 1},
            {0, 0, -1, -1, -1, -1, -1, 1, 1},
            {1, 1, 0, 0, 0, -1, -1, 1, 1},
            {1, 1, 0, 0, 0, -1, -1, 1, 1},
            {1, 1, 1, 1, 0, -1, -1, 1, 1},
            {1, 1, 1, 1, 1, -1, -1, 1, 1},
            {-1, -1, -1, -1, -1, -1, -1, 0, -2},
            {1, 1, 1, 1, 1, 1, -2, 1, 1},
            {-1, -1, -1, -1, -1, -1, -1, -2, 0},
    };

    // some common math constant
    public final static double PI = 3.1415926535;
    public final static double E = 2.7182818284590;

    // enumeration for operators
    public static enum OPTR { PLUS, MINUS, MULTIPLY, DIVIDE, CONTRAST };
    // priority for operators
    public static enum PRIORITY { SUPERIOR, EQUAL, INFERIOR, BAD };

    // exception
    class WrongOperator extends Exception {
        private char key;
        public WrongOperator(char i) {
            this.key = i;
        }
        @Override
        public String toString() {
            return "Unexpected operator: " + this.key + '.';
        }
    }
    class BracketsDismatchment extends Exception {
        private int key;
        public BracketsDismatchment(int val) {
            this.key = val;
        }
        @Override
        public String toString() {
            return "Dis-match for brackets at: " + this.key + '.';
        }
    }
    class ParametersEmpty extends Exception {
        @Override
        public String toString() {
            return "Parameters are needed some where.(within brackets or between operators)";
        }
    }
    class BadEnd extends Exception {
        @Override
        public String toString() {
            return "Expression ended unexpected.";
        }
    }
}
