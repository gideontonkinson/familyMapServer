package requestresult;

/** Serialized HTTP register result */
public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success = true;
    private String message;

    /**
     * Creates a successful RegisterResult
     * @param authtoken
     * @param username
     * @param personID
     */
    public RegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Creates a fail RegisterResult
     * @param message
     * @param success
     */
    public RegisterResult(String message, boolean success) {
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
