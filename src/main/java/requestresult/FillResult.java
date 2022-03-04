package requestresult;

/** Serialized HTTP add event request */
public class FillResult {
    private String message;
    private boolean success = true;

    /**
     * Creates a successful FillRequest
     * @param message
     */
    public FillResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail FillResult
     * @param message
     * @param success
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
