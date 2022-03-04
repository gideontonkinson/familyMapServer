package requestresult;

/** Serialized HTTP load result */
public class LoadResult {
    private String message;
    private boolean success = true;

    /**
     * Creates a successful LoadResult
     * @param message
     */
    public LoadResult(String message) {
        this.message = message;
    }

    /**
     * Creates a fail LoadResult
     * @param message
     * @param success
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
