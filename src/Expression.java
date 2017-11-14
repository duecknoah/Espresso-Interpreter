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
        return (e == '+' || e == '-' || e == '*' || e == '/');
    }

    /**
     * Checks if e is an operand
     * @return true if operand, false otherwise
     */
    private static boolean isOperand(char e) {
        // numeric characters will be converted to there integer value
        if (isInteger(e))
            return true;
        if (isLetter(e))
            return true;
        return false;
    }

    private static boolean isInteger(char e) {
        return e >= '0' && e <= '9';
    }

    private static boolean isLetter(char e) {
        return e >= 'A' && e <= 'z';
    }

    /**
     * Checks if 'op' is a lower precedence than 'otherOp'
     * @return true if 'op' is lower, false otherwise
     */
    private static boolean isLowerPrecedence(char op, char otherOp) {
        // Valid operators in order of lowest to highest precedence
        String operators = "-+*/";
        int opLevel = operators.indexOf(op);
        int otherOpLevel = operators.indexOf(otherOp);

        return opLevel < otherOpLevel;
    }

    /**
     * Converts the given prefix expression to postfix
     * @return a postfix expression of type String
     */
    public static String convertToPostFix(String expression) throws VariableNameException, InvalidTokenException, ParenthesesException {
        Stack<Character> stack = new Stack<Character>();
        char[] expressionAsArray = expression.toCharArray();
        String output = "";
        int i = 0;

        while (i < expressionAsArray.length) {
            char e = expressionAsArray[i];
            System.out.println(e + " output = " + output);
            System.out.println(stack);
            // Append to output string if e is an operand
            if (isOperand(e)) {
                // Keep concatinating if its a number, as numbers may have
                // multiple digits in a row
                String temp = String.valueOf(e);
                boolean isInt = isInteger(e);
                // keep appending If the first token was an int, and the next one is
                while (i + 1 < expressionAsArray.length && isInt && isInteger(expressionAsArray[i + 1])) {
                    e = expressionAsArray[++i];
                    temp += e;
                    isInt = true;
                }
                
                // Check for invalid variable names (ex. 2y or y2)
                // If there is another token after 'e'
                if (i + 1 < expressionAsArray.length) {
                    // If we know this is an integer already
                    if (isInt) {
                        // If there is a letter attatched to the integer, mark it as not a variable
                        if (isLetter(expressionAsArray[i + 1]))
                            throw new VariableNameException(temp + expressionAsArray[i + 1] + " is not a variable");
                    }
                    else 
                        if (isInteger(expressionAsArray[i + 1]))
                            throw new VariableNameException(temp + expressionAsArray[i + 1] + " is not a variable");
                }

                output += temp + " ";
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
                    while (!stack.isEmpty() && !isLowerPrecedence(stack.peek(), e) && stack.peek() != '(')
                        output += stack.pop() + " ";
                    stack.push(e);
                }
            }
            /* if e is “)” , pop operators from the stack and append them to the output 
             * string until you see matched “(” (NOTE: you should also pop “(” )
            */
            while (e == ')') {
                if (stack.isEmpty())
                    throw new ParenthesesException("Unbalanced parentheses: Too many closing parenthesis.");
                boolean isOpenBracket = (stack.peek() == '(');
                if (!isOpenBracket)
                    output += stack.pop() + " ";
                else {
                    stack.pop();
                    break;
                }
            }
            // Increment i
            i ++;
            if (i < expressionAsArray.length) {
                // Verify that there is a space here
                if (expressionAsArray[i] != ' ')
                    throw new InvalidTokenException("Invalid token " + expressionAsArray[i]);
                }
            i ++;
        }

        // Lastly, pop all operators and append them to the output
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                throw new ParenthesesException("Unbalanced parentheses: Too many opening parenthesis.");
            output += stack.pop() + " ";
        }

        // Return output (and get rid of any extra spaces)
        return output.trim();
    }

    public static void main(String[] args) {
        // Valid expressions
        try {
            System.out.println(Expression.convertToPostFix("( ( ( ( 5 ) * 2 ) ) )"));
            System.out.println(Expression.convertToPostFix("1 * ( 2 + 3 / ( 4 ) )"));
            System.out.println(Expression.convertToPostFix("Y"));
            System.out.println(Expression.convertToPostFix("X + 2 * y"));
            System.out.println(Expression.convertToPostFix("( 0 - 45 )"));
            System.out.println(Expression.convertToPostFix("( x - y ) * ( x + y )"));
            System.out.println(Expression.convertToPostFix("G * ( ( m * n ) / ( r * r ) )"));
            System.out.println(Expression.convertToPostFix("5 / 4 * 3 + 2 - 1"));
            System.out.println(Expression.convertToPostFix("1 + 2 + 3"));
        }
        catch (ESPException e) {
            e.printStackTrace();
        }
    }
}