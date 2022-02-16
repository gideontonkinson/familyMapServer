package requestresult;

public class ResultException extends Exception{
    private boolean success = false;

    /**
     * Creates an unsuccessful Result for all Result Classes
     * @param message
     */
    public ResultException(String message) {
        super(message);
    }
}
