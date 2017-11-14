// An exception thrown when there is unbalanced parentheses
public class ParenthesesException extends InvalidSyntaxException {
    // Parameterless Constructor
    public ParenthesesException() {}
    
    // Constructor that accepts a message
    public ParenthesesException(String message)
    {
        super(message);
    }  
}
