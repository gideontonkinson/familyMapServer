package requestresult;

public class GetEventResult {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private boolean success = true;

    /**
     * Creates a successful GetEventResult
     * @param associatedUsername
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public GetEventResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }
}
