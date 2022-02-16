package model;

public class User implements Model{
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * Creates a User
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
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
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        generatePersonID();
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

    /**
     * Generates a unique personID on creation
     */
    private void generatePersonID(){
        String uniquePersonID = "";
        this.personID = uniquePersonID;
    }
}
