package model;

public class User implements Model{
    /** Username for the user */
    private String username;
    /** Password for the user */
    private String password;
    /** Email of the user */
    private String email;
    /** First name of the user */
    private String firstName;
    /** Last name of the user */
    private String lastName;
    /** Gender of the user */
    private String gender;
    /** Unique ID for the User that is associated with a person object */
    private String personID;

    /**
     * Creates a User
     * @param username Username for the user
     * @param password Password for the user
     * @param email Email of the user
     * @param firstName First name of the user
     * @param lastName Last name of the user
     * @param gender Gender of the user
     * @param personID Unique ID for the User that is associated with a person object
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * Creates a new User and generates a unique personID
     * @param username Username for the user
     * @param password Password for the user
     * @param email Email of the user
     * @param firstName First name of the user
     * @param lastName Last name of the user
     * @param gender Gender of the user
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = generateUniqueID();
    }

    @Override
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

    public String getPersonID() {
        return personID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
