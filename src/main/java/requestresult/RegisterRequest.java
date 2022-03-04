package requestresult;

/** Serialized HTTP register request */
public class RegisterRequest {
    /** Username of user */
    String username;
    /** Password of user */
    String password;
    /** Email of user */
    String email;
    /** First name of user */
    String firstName;
    /** Last name of user */
    String lastName;
    /** Gender of user */
    String gender;

    /**
     * Creates a RegisterRequest
     * @param username Username of user
     * @param password Password of user
     * @param email Email of user
     * @param firstName First name of user
     * @param lastName Last name of user
     * @param gender Gender of user
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
