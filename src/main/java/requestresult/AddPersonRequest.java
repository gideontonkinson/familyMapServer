package requestresult;

/** Serialized HTTP add person request */
public class AddPersonRequest {
    /** Unique ID for the person */
    private String personID;
    /** Username associated with the Person */
    private String associatedUsername;
    /** First name of the person */
    private String firstName;
    /** Last name of the person */
    private String lastName;
    /** Gender of the person */
    private String gender;
    /** Unique ID for the father */
    private String fatherID;
    /** Unique ID for the mother */
    private String motherID;
    /** Unique ID for the spouse */
    private String spouseID;

    /**
     * Creates an AddPersonRequest
     * @param personID Unique ID for the person
     * @param associatedUsername Username associated with the Person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     * @param fatherID Unique ID for the father
     * @param motherID Unique ID for the mother
     * @param spouseID Unique ID for the spouse
     */
    public AddPersonRequest(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
