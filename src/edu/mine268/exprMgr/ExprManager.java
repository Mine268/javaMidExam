package edu.mine268.exprMgr;

import edu.mine268.numStructure.*;
import edu.mine268.numStructure.concrete.Digital;
import edu.mine268.optrStructure.*;

import java.util.Queue;
import java.util.LinkedList;

/**
 * This class is a class for Expression Calculation and
 * Saving use, each instance is an expression.
 * @author mine268
 * @version 0.0
 */
public class ExprManager implements ConstSome {
    private final Node root;

    /**
     * Require a string of expression and construct the
     * expression in the form of tree.
     * @author mine268
     * @param stb The expression in the form of text
     */
    public ExprManager(StringBuilder stb) throws Exception {
        if (!isNumeric(stb.charAt(stb.length() - 1)) && stb.charAt(stb.length() - 1) != ')')
            throw new BadEnd();

        // brackets checks
        int count_t = 0, ind = 0;
        do {
            if (stb.charAt(ind) == '(') count_t++;
            else if (stb.charAt(ind) == ')') count_t--;
            ind++;
        } while (ind < stb.length() && count_t >= 0);
        if (count_t != 0) throw new BracketsDismatchment(ind);

        // in case that the first word is negative
        if (stb.charAt(0) == '-') {
            int end = 1;
            if (stb.charAt(end) == '(') {
                int count = 0;
                do {
                    if (stb.charAt(end) == '(') count++;
                    else if (stb.charAt(end) == ')') count--;
                    end++;
                } while (count != 0);
            } else {
                while (isNumeric(stb.charAt(end)))
                    end++;
            }

            stb.insert(end, ')');
            stb.insert(0, "(0");
        }

        char[] exprChars = stb.toString().toCharArray();
        this.root = buildTree(exprChars, 0, exprChars.length, (exprChars[0] == '-' && exprChars[1] == '('));
    }

    /**
     * @author mine268
     * Inner method to build the tree
     * @param expChars string to build the tree
     * @param st head of the string to be processed
     * @param ed the latter of the end of the string to be processed
     * @param conp indicates whether 0 should be added to the head of
     *             the expression string for instance that the first
     *             number(or ( and ) string) follows a minus symbol
     *             representing negative symbol
     */
    private static Node buildTree(char[] expChars, int st, int ed, boolean conp) throws Exception {
        if (st >= ed) throw new ParametersEmpty();
        // Queue to save the operators and numbers at the current level
        Queue<Node> numbs = new LinkedList<>();
        Queue<Operator> optrs = new LinkedList<>();
        int i = st, j = st;
        Node subroot;

        if (conp) {
            numbs.offer(new Digital(0));
            optrs.offer(new Operator('-'));
            i++;
            j++;
        }

        /* the coming element can only be
        - number, pos or neg
        - expression in the pair, without a head neg sym
        */
        while (i < ed) {
            // expression starts with a number
            if (isNumeric(expChars[i]) || expChars[i] == '-' && isNumeric(expChars[i + 1])) {
                if (expChars[i] == '-') j++;
                // indexing the end of the number
                while (j < expChars.length && isNumeric(expChars[j]))
                    j++;

                Node tmp = new Digital(Double.parseDouble(String.valueOf(expChars).substring(i, j)));
                tmp.sealed = true;
                numbs.offer(tmp);
            } else { // if (expChars[i] =='(')
                // find the head and the tail of the pair
                int count = 0;
                do {
                    if (expChars[j] == '(') count++;
                    else if (expChars[j] == ')') count--;
                    j++;
                } while (count != 0);

                Node tmp = buildTree(expChars, i + 1, j - 1, expChars[i + 1] == '-' && expChars[i + 2] == '(');
                tmp.sealed = true;
                numbs.offer(tmp);
            }
            // then process the operator
            i = j;
            if (i < ed) {
                optrs.offer(new Operator(expChars[i]));
                i++;
                j++;
            }
        }

        if (numbs.isEmpty())
            subroot = null;
        else if (optrs.isEmpty())
            subroot = numbs.poll();
        else {
            subroot = numbs.poll();
            while (!numbs.isEmpty()) {
                Node cur = subroot;
                Operator optmp = optrs.poll();
                Node nbtmp = numbs.poll();
                while (!cur.sealed &&
                        ExprManager.proceed(optmp.getType(), ((Operator)cur).getType()) == PRIORITY.SUPERIOR)
                    cur = cur.children[1];

                if (cur.parent == null)
                    subroot = optmp;
                else {
                    cur.parent.children[1] = optmp;
                    optmp.parent = cur.parent;
                }

                optmp.children[1] = nbtmp;
                optmp.children[0] = cur;
                nbtmp.parent = optmp;
                cur.parent = optmp;
            }
        }

        return subroot;
    }

    /**
     * @author mine268
     * do the calculation based on the root node.
     * @return double
     */
    public double calculate() {
        if (this.root instanceof NumberNode)
            return ((NumberNode) this.root).getValue();
        else if (this.root instanceof Operator)
            return ((Operator) this.root).calculate();
        return Double.NaN;
    }

    /**
     * @author mine268
     * compare the priority of two operations.
     * @param op1 operation left
     * @param op2 operation right
     * @return PRIORITY
     */
    private static PRIORITY proceed(final OPTR op1, final OPTR op2) {
        return switch (ConstSome.table[ExprManager.convert(op1)][ExprManager.convert(op2)]) {
            case -1 -> PRIORITY.INFERIOR;
            case 0 -> PRIORITY.EQUAL;
            case 1 -> PRIORITY.SUPERIOR;
            default -> PRIORITY.BAD;
        };
    }

    /**
     * @author mine268
     * convert the operation to the exact number in the table of priority.
     * @param optr operation
     * @return int
     */
    private static int convert(OPTR optr) {
        return switch (optr) {
            case PLUS -> 0;
            case MINUS -> 1;
            case MULTIPLY -> 2;
            case DIVIDE -> 3;
            default -> -1;
        };
    }

    /**
     * check if it is a number or the point
     * @author mine268
     * @return boolean
     */
    private static boolean isNumeric(char c) {
        return c >= '0' && c <= '9' || c == '.';
    }
}
