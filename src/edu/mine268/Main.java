package edu.mine268;

import edu.mine268.exprMgr.*;
import java.util.Scanner;

public class Main {

    private static final String previews = """
            This is a calculator developed by Mine268(rkw), supporting only four operators 
            and brackets. The numbers fall in [-9223372036854775808,9223372036854775807].
            Type your expression, press ENTER and the programme will feedback the result 
            or some info on the errors in the expression if exist. Enter '/quit' to terminate.
            The programme will be accessible from https://www.github.com/mine268
            Any suggestions are welcomed.
            """;

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        ExprManager exp;
        long count = 0;
        System.out.println(previews);

        while (true) {
            try {
                System.out.printf("<%%i%d> ", count);
                String enter = reader.nextLine();
                if (enter.equals("/quit")) break;
                exp = new ExprManager(new StringBuilder(enter));
                System.out.printf("<%%o%d> %f\n", count, exp.calculate());
            } catch (Exception ex) {
                System.out.printf("<%%o%d> \n", count);
                System.out.println(ex.toString());
            }
            count++;
        }
        System.out.println("GoodBye");
    }
}
