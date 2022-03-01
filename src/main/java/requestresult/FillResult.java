package requestresult;

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

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
