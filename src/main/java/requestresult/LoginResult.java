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

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public boolean isSuccess() {
        return success;
    }
}
