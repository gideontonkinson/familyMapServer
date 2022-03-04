package model;

public class Person implements Model{
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
     * Creates a Person and generates a unique personID on creation
     * @param associatedUsername Username associated with the Person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     * @param fatherID Unique ID for the father
     * @param motherID Unique ID for the mother
     * @param spouseID Unique ID for the spouse
     */
    public Person(String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = generateUniqueID();
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Creates a Person
     * @param personID Unique ID for the person
     * @param associatedUsername Username associated with the Person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     * @param fatherID Unique ID for the father
     * @param motherID Unique ID for the mother
     * @param spouseID Unique ID for the spouse
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Creates a Person
     * @param personID Unique ID for the person
     * @param associatedUsername Username associated with the Person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Creates a new Person and generates a unique personID on creation
     * @param associatedUsername Username associated with the Person
     * @param firstName First name of the person
     * @param lastName Last name of the person
     * @param gender Gender of the person
     */
    public Person(String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = generateUniqueID();
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }



    public String getPersonID() {
        return personID;
    }

    @Override
    public String getUsername() {
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

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
