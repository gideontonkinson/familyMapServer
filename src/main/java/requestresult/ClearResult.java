package requestresult;

/** Serialized HTTP clear result */
public class ClearResult {
    /** Message detailing what happened */
    private String message;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;

    /**
     * Creates a successful ClearResult
     * @param message message detailing this succeeded
     */
    public ClearResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail ClearResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
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
