package requestresult;

public class ClearResult {
    private String message;
    private boolean success = true;

    /**
     * Creates a successful clearResult
     * @param message
     */
    public ClearResult(String message) {
        this.message = message;
    }
}
