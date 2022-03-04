package requestresult;

/** Serialized HTTP login request */
public class LoginRequest {
    /** Username of user */
    private String username;
    /** Password of user */
    private String password;

    /**
     * Creates a LoginRequest
     * @param username username of user
     * @param password password of user
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
