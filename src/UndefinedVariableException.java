// An exception thrown when a variable that 
// exists but is undefined
public class UndefinedVariableException extends ESPException {
    // Parameterless Constructor
    public UndefinedVariableException() {}
    
    // Constructor that accepts a message
    public UndefinedVariableException(String message)
    {
        super(message);
    }
}
