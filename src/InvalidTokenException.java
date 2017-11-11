// An exception thrown when an invalid token is detected
public class InvalidTokenException extends ESPException {
    // Parameterless Constructor
    public InvalidTokenException() {}
    
    // Constructor that accepts a message
    public InvalidTokenException(String message)
    {
        super(message);
    }  
}
