package requestresult;

/** Serialized HTTP clear result */
public class ClearResult {
    private String message;
    private boolean success = true;

    /**
     * Creates a successful ClearResult
     * @param message
     */
    public ClearResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail ClearResult
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {
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
