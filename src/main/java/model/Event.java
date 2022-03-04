package model;

public class Event implements Model{
    /** Unique ID for the event */
    private String eventID;
    /** Username associated with the Event */
    private String associatedUsername;
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


    /**
     * Creates an Event
     * @param eventID Unique ID for the event
     * @param associatedUsername Username associated with the AuthToken
     * @param personID Unique ID for the person associated with the event
     * @param latitude Latitude of event
     * @param longitude Longitude of event
     * @param country Country of the event
     * @param city City of the event
     * @param eventType Event type
     * @param year Year of the event
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Creates a new Event and generates a unique eventID on creation
     * @param associatedUsername Username associated with the AuthToken
     * @param personID Unique ID for the person associated with the event
     * @param latitude Latitude of event
     * @param longitude Longitude of event
     * @param country Country of the event
     * @param city City of the event
     * @param eventType Event type
     * @param year Year of the event
     */
    public Event(String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.eventID = generateUniqueID();
    }

    public String getEventID() {
        return eventID;
    }

    @Override
    public String getUsername() {
        return associatedUsername;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
