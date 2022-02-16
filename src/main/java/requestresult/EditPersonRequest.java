package requestresult;

public class EditPersonRequest {
    private String personID;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * Creates a EditPersonRequest
     * @param personID
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public EditPersonRequest(String personID, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
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
