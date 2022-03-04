package model;

public class AuthToken implements Model{
    /** Unique AuthToken */
    private String authtoken;
    /** Username associated with the AuthToken */
    private String username;

    /**
     * Creates an AuthToken
     * @param authtoken Unique AuthToken
     * @param username Username associated with the AuthToken
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    /**
     * Creates a new AuthToken and generates the authtoken
     * @param username Username associated with the AuthToken
     */
    public AuthToken(String username) {
        this.username = username;
        this.authtoken = generateUniqueID();
    }

    public String getAuthtoken() {
        return authtoken;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
