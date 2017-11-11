// An exception thrown when the variable name is invalid
// and doesn't exist in variable dictionary
public class VariableNameException extends ESPException {
    // Parameterless Constructor
    public VariableNameException() {}
    
    // Constructor that accepts a message
    public VariableNameException(String message)
    {
        super(message);
    }
}
