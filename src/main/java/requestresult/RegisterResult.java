package requestresult;

public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success = true;

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
}
