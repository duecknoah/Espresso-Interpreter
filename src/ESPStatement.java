public enum ESPStatement {
    // Statements used in the Espresso Lang
    COMMENT, // Comments are ignored lines
    INPUT, // gets user input and stores it in 'x' where 'read x'
    OUTPUT, // prints 'x' to the screen where 'x' in 'print x'
    ASSIGNMENT, // assigns a value or infix expression value to a variable
    IF, // If statement
    RUN, // jumps to the desired function 'x' using 'run x'
    FUNCTION, // a function allows program to re-run a piece of code using 'function x'
    RETURN; // exits a function and returns the value 'x' in 'return x'
    /**
     * Other Statement notes:
     * Comments:
     * - Comments are used by having an empty line beginning a line with '//'
     * Infix Expressions:
     * - expressions can be used ONLY in OUTPUT, ASSIGNMENT, or RETURN statements
     * Functions:
     * - functions MUST be defined before they are run
     */

    /**
     * Returns the type of line statement 'line' is
     * Any Errors captured here are syntax errors.
    */
    public static ESPStatement getType(String line) throws InvalidSyntaxException {
        // Check common keywords
        if (line.startsWith("//") || line.equals("")) {
            return COMMENT;
        }
        if (line.startsWith("read ")) {
            if (line.length() != 6)
                throw new InvalidSyntaxException("Invalid syntax error for read statement");
            return INPUT;
        }
        if (line.startsWith("print ")) {
            if (line.length() < 6)
                throw new InvalidSyntaxException("Invalid syntax error for print statement");
            return OUTPUT;
        }
        if (line.startsWith("if ")) {
            return IF;
        }
        if (line.startsWith("run ")) {
            if (line.length() != 5)
                throw new InvalidSyntaxException("Invalid syntax error for run statement");
            return RUN;
        }
        if (line.startsWith("return ")) {
            if (line.length() < 8)
                throw new InvalidSyntaxException("Invalid syntax error for return statement");
            return RETURN;
        }
        if (line.startsWith("func ")) {
            if (line.length() != 6)
                throw new InvalidSyntaxException("Invalid syntax error for function statement");
            return FUNCTION;
        }

        // Since it wasn't a keyword, it must be an assignment statement.
        // So verify that it matches (x = x)
        if (line.matches("\\w\\s=\\s.+"))
            return ASSIGNMENT;

        throw new InvalidSyntaxException("Unknown statement type");
    }
}
