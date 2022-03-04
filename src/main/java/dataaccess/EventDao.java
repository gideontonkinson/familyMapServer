package dataaccess;

import model.Model;
import model.Event;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/** Data Access Object to interact with the Event table */
public class EventDao implements Dao{
    /** Connection to the family map database */
    private final Connection conn;

    /**
     * Creates an EventDao with a connection to a database
     * @param conn Connection to the database
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets an Event from the database
     * @param eventID Event to from the database
     * @return Event created from the database data, null if Event does not exist
     * @throws DaoException if data could not be accessed
     */
    @Override
    public Event getFromDB(String eventID) throws DaoException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding Event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Adds an Event to the database
     * @param eventM Event to add to the database
     * @return true if the Event was added, false if the Event already exists for the Person
     * @throws DaoException if object passed was not an Event or if the Event could not be added
     */
    @Override
    public boolean addToDB(Model eventM) throws DaoException {
        Event event = (Event)eventM;
        String sql = "INSERT INTO Events (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error encountered while inserting Event into the database");
        }
        return true;
    }

    /**
     * Returns a list of all Events for a Person
     * @param personID Person to get the events of
     * @return List of Events for a personID
     * @throws DaoException if data could not be accessed
     */
    public List<Event> getEventsForPerson(String personID) throws DaoException {
        List<Event> events = new ArrayList<Event>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            while(rs.next()){
                Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                events.add(event);
            }
            if(events.size() > 0) {
                return events;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding Events");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Returns a list of all Events for a User
     * @param username Username to get the events of
     * @return List of Events for a username
     * @throws DaoException if data could not be accessed
     */
    public List<Event> getEventsForUser(String username) throws DaoException {
        List<Event> events = new ArrayList<Event>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                events.add(event);
            }
            if(events.size() > 0) {
                return events;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding Events");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Removes all Events for a User
     * @param username username to clear the events for
     * @throws DaoException if data could not be accessed
     */
    public void clearEventsForUser(String username) throws DaoException {
        String sql = "DELETE FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while clearing Events");
        }
    }

    /**
     * Updates the associatedUsername of all Events with that associatedUsername
     * @param username username of the user
     * @param newUsername new username to update
     * @return true if the username was updated, false if the User does not exist
     * @throws DaoException if the username could not be updated
     */
    public boolean updateUsername(String username, String newUsername) throws DaoException {
        int r;
        String sql = "UPDATE Events SET associatedUsername = ?  WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating username on Events");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the longitude of an Event
     * @param eventID Event to update
     * @param longitude new logitude
     * @return true if the longitude was updated, false if the Event does not exist
     * @throws DaoException if the longitude could not be updated
     */
    public boolean updateLongitude(String eventID, float longitude) throws DaoException {
        int r;
        String sql = "UPDATE Events SET longitude = ?  WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, longitude);
            stmt.setString(2, eventID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating longitude on Event");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the latitude of an Event
     * @param eventID Event to update
     * @param latitude new latitude
     * @return true if the latitude was updated, false if the Event does not exist
     * @throws DaoException if the latitude could not be updated
     */
    public boolean updateLatitude(String eventID, float latitude) throws DaoException {
        int r;
        String sql = "UPDATE Events SET latitude = ?  WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, latitude);
            stmt.setString(2, eventID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating latitude on Event");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the country of an Event
     * @param eventID Event to update
     * @param country new country
     * @return true if the country was updated, false if the Event does not exist
     * @throws DaoException if the country could not be updated
     */
    public boolean updateCountry(String eventID, String country) throws DaoException {
        int r;
        String sql = "UPDATE Events SET country = ?  WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, country);
            stmt.setString(2, eventID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating country on Event");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the city of an Event
     * @param eventID Event to update
     * @param city new city
     * @return true if the country was updated, false if the Event does not exist
     * @throws DaoException if the city could not be updated
     */
    public boolean updateCity(String eventID, String city) throws DaoException {
        int r;
        String sql = "UPDATE Events SET city = ?  WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, city);
            stmt.setString(2, eventID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating city on Event");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the year of an Event
     * @param eventID Event to update
     * @param year new year
     * @return true if the country was updated, false if the Event does not exist
     * @throws DaoException if the year could not be updated
     */
    public boolean updateYear(String eventID, int year) throws DaoException {
        int r;
        String sql = "UPDATE Events SET year = ?  WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            stmt.setString(2, eventID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating year on Event");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Clears the Event table
     * @throws DaoException if there is an error while clearing
     */
    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("Error: SQL Error encountered while clearing Events table");
        }
    }

}
