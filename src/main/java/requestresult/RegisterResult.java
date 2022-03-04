package requestresult;

/** Serialized HTTP register result */
public class RegisterResult {
    /** Unique AuthToken of user */
    private String authtoken;
    /** Username of user */
    private String username;
    /** Unique Person ID of user */
    private String personID;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful RegisterResult
     * @param authtoken Unique AuthToken of user
     * @param username Username of use
     * @param personID Unique Person ID of user
     */
    public RegisterResult(String authtoken, String username, String personID) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Creates a fail RegisterResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
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
