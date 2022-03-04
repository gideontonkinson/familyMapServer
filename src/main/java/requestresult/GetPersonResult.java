package requestresult;

/** Serialized HTTP get persons result */
public class GetPersonResult {
    /** Username associated with the Person */
    private String associatedUsername;
    /** Unique ID for the person */
    private String personID;
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
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful GetPersonResult
     * @param associatedUsername Username associated with the Person
     * @param personID Unique ID for the person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     * @param fatherID Unique ID for the father
     * @param motherID Unique ID for the mother
     * @param spouseID Unique ID for the spouse
     */
    public GetPersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Creates a fail GetPersonResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public GetPersonResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
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

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
