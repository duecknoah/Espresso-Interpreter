// A General Exception for the Espresso Language. All
// other exceptions extend from this
public class ESPException extends Exception {
	// Parameterless Constructor
    public ESPException() {}

    // Constructor that accepts a message
    public ESPException(String message)
    {
       super(message);
    }
}
