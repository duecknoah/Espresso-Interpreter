// An exception thrown when a unary operator is detected
// in a infix expression
public class UnaryOperatorException extends ESPException {
    // Parameterless Constructor
    public UnaryOperatorException() {}
    
    // Constructor that accepts a message
    public UnaryOperatorException(String message)
    {
        super(message);
    }
}
