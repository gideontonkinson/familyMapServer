package requestresult;

public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success = true;
    private String message;

    /**
     * Creates a successful LoginResult
     * @param authtoken
     * @param username
     * @param personID
     */
    public LoginResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Creates a fail LoginResult
     * @param message
     * @param success
     */
    public LoginResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
