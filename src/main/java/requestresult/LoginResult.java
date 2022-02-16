package requestresult;

public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private boolean success = true;

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
}
