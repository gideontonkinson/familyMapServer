package model;

public class AuthToken implements Model{
    private String authtoken;
    private String username;

    /**
     * Creates an AuthToken
     * @param authtoken
     * @param username
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    /**
     * Creates a new AuthToken and generates the authtoken
     * @param username
     */
    public AuthToken(String username) {
        this.username = username;
        getAuthtoken();
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

    /**
     * Generates a unique personID on creation
     */
    private void generateAuthtoken(){
        String uniqueAuthtoken = "";
        this.authtoken = uniqueAuthtoken;
    }
}
