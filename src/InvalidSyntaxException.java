// An exception thrown for when invalid syntax exists in a line
public class InvalidSyntaxException extends ESPException {
    // Parameterless Constructor
    public InvalidSyntaxException() {}
    
    // Constructor that accepts a message
    public InvalidSyntaxException(String message)
    {
        super(message);
    }
}
