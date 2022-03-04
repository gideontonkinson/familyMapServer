package requestresult;

/** Serialized HTTP add event request */
public class FillResult {
    /** Message detailing what happened */
    private String message;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;

    /**
     * Creates a successful FillRequest
     * @param message message detailing this succeeded
     */
    public FillResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail FillResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
