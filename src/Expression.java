import java.util.Stack;

/**
 * The Expression class Does convertions from infix 
 * expressions to postfix.
 * It also can evaluate postfix expressions.
 */
public final class Expression {

    /** 
     * Checks if e is an operator
     * @return true if e is an operator, false otherwise
     * valid operators: + - * / %
     */
    private static boolean isOperator(char e) {
        return (e == '+' || e == '-' || e == '*' || e == '/' || e == '%');
    }

    /**
     * Checks if e is an operand
     * @return true if operand, false otherwise
     */
    private static boolean isOperand(char e) {
        // numeric characters will be converted to there integer value
        if (e >= '0' && e <= '9')
            return true;
        if (e >= 'A' && e <= 'z')
            return true;
        return false;
    }

    /**
     * Checks if 'op' is a lower precedence than 'otherOp'
     * @return true if 'op' is lower, false otherwise
     */
    private static boolean isLowerPrecedence(char op, char otherOp) {
        switch (op) {
            case '+':
            case '-':
                return !(otherOp == '+' || otherOp == '-');
            case '*':
            case '/':
                return !(otherOp == '^');
            default:
                return false;
        }
    }

    /**
     * Converts the given prefix expression to postfix
     * @return a postfix expression of type String
     */
    public static String convertToPostFix(String expression) {
        Stack<Character> stack = new Stack<Character>();
        String output = "";

        for (char e : expression.toCharArray()) {
            if (e == ' ')
                continue;
            System.out.println(e + " output = " + output);
            System.out.println(stack);
            // Append to output string if e is an operand
            if (isOperand(e)) {
                output += e;
            }
            else if (e == '(')
                stack.push(e);
            else if (isOperator(e)) {
                if (stack.isEmpty())
                    stack.push(e);
                else {
                    /* pop operators of greater or equal precedence from the
                     * stack and append them to the output string, stop when you see an
                     * operator of lower precedence or “(”
                    */
                    while (isLowerPrecedence(e, stack.peek()) && stack.peek() != '(')
                        output += stack.pop();
                    stack.push(e);
                }
            }
            /* if e is “)” , pop operators from the stack and append them to the output 
             * string until you see matched “(” (NOTE: you should also pop “(” )
            */
            while (e == ')') {
                char stackPeek = stack.peek();
                output += stack.pop();

                if (stackPeek == '(')
                    break;
            }
        }

        // Lastly, pop all operators and append them to the output
        while (!stack.isEmpty())
            output += stack.pop();

        return expression;
    }
}