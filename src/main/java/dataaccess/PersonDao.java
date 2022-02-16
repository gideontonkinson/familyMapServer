package dataaccess;

import model.Event;
import model.Model;
import model.Person;

import java.sql.*;
import java.util.List;

public class PersonDao implements Dao{
    private final Connection conn;

    /**
     * Creates a PersonDao with a connection to a database
     * @param conn
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets a Person from the database
     * @param personID
     * @return Person created from database data
     * @throws DaoException if data could not be accessed or Person does not exist
     */
    @Override
    public Person getFromDB(String personID) throws DaoException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person= new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error encountered while finding Person");
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
     * Adds a Person to the database
     * @param personM
     * @return true if the Person was added, false if the Person already exists
     * @throws DaoException if object passed was not a Person or if the Person could not be added
     */
    @Override
    public boolean addToDB(Model personM) throws DaoException {
        Person person = (Person)personM;
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID," +
                "motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error encountered while inserting Person into the database");
        }
        return true;
    }

    /**
     * Returns a list of all Persons for User
     * @param username
     * @return List of Persons for a username
     * @throws DaoException if data could not be accessed
     */
    public List<Person> getPersonsForUser(String username) throws DaoException {
        return null;
    }

    /**
     * Updates the fatherID of a Person
     * @param personID
     * @param fatherID
     * @return true if fatherID was updated, false if the Person or father does not exist
     * @throws DaoException if the fatherID could not be updated
     */
    public boolean updateFather(String personID, String fatherID) throws DaoException {
        return true;
    }

    /**
     * Updates the motherID of a Person
     * @param personID
     * @param spouseID
     * @return true if the motherID was updated, false if the Person or mother does not exist
     * @throws DaoException if the motherID could not be updated
     */
    public boolean updateMother(String personID, String spouseID) throws DaoException {
        return true;
    }

    /**
     * Updates the spouseID of a Person
     * @param personID
     * @param spouseID
     * @return true fi the spouseID was updated, false if the Person or spouse does not exist
     * @throws DaoException if the spouseID could not be updated
     */
    public boolean updateSpouse(String personID, String spouseID) throws  DaoException {
        return true;
    }

    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("SQL Error encountered while clearing Person table");
        }
    }
}