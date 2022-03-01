package requestresult;

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

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
