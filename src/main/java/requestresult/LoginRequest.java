package requestresult;

/** Serialized HTTP login request */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Creates a LoginRequest
     * @param username
     * @param password
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
