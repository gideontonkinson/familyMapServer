package requestresult;

/** Serialized HTTP load result */
public class LoadResult {
    /** Message detailing what happened */
    private String message;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;

    /**
     * Creates a successful LoadResult
     * @param message message detailing this succeeded
     */
    public LoadResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail LoadResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public LoadResult(String message, boolean success) {
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
