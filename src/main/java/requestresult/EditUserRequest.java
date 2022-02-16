package requestresult;

public class EditUserRequest {
    private String username;
    private String password;
    private String email;
    private String personID;


    /**
     * Creates an EditUserRequest
     * @param username
     * @param password
     * @param email
     * @param personID
     */
    public EditUserRequest(String username, String password, String email, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonID() {
        return personID;
    }
}
