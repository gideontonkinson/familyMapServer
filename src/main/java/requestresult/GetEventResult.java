package requestresult;

/** Serialized HTTP get event result */
public class GetEventResult {
    /** Username associated with the Event */
    private String associatedUsername;
    /** Unique ID for the event */
    private String eventID;
    /** Unique ID for the person associated with the event */
    private String personID;
    /** Latitude of event */
    private float latitude;
    /** Longitude of event */
    private float longitude;
    /** Country of the event */
    private String country;
    /** City of the event */
    private String city;
    /** Event type */
    private String eventType;
    /** Year of the event */
    private int year;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful GetEventResult
     * @param associatedUsername Username associated with the AuthToken
     * @param eventID Unique ID for the event
     * @param personID Unique ID for the person associated with the event
     * @param latitude Latitude of event
     * @param longitude Longitude of event
     * @param country Country of the event
     * @param city City of the event
     * @param eventType Event type
     * @param year Year of the event
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

    /**
     * Creates a fail GetEventResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public GetEventResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
