package requestresult;

public class EditEventRequest {
    private String eventID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private int year;

    /**
     * Creates a EditEventRequest
     * @param eventID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param year
     */
    public EditEventRequest(String eventID, float latitude, float longitude, String country, String city, int year) {
        this.eventID = eventID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
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

    public int getYear() {
        return year;
    }
}
