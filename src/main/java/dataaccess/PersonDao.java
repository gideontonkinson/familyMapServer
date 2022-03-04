package dataaccess;

import model.Model;
import model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Data Access Object to interact with the Person table */
public class PersonDao implements Dao{
    /** Connection to the family map database */
    private final Connection conn;

    /**
     * Creates a PersonDao with a connection to a database
     * @param conn Connection to the database
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets a Person from the database
     * @param personID Person to get from database
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
            throw new DaoException("Error: Encountered error while finding Person");
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
     * @param personM Person to add
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
            throw new DaoException("Error: Encountered error while inserting Person into the database");
        }
        return true;
    }

    /**
     * Returns a list of all Persons for User
     * @param username username to get Persons for
     * @return List of Persons for a username
     * @throws DaoException if data could not be accessed
     */
    public List<Person> getPersonsForUser(String username) throws DaoException {
        List<Person> persons = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                persons.add(person);
            }
            if(persons.size() > 0) {
                return persons;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding Person");
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
     * Removes all Persons for User
     * @param username username to clear Persons for
     * @throws DaoException if data could not be accessed
     */
    public void clearPersonsForUser(String username) throws DaoException {
        String sql = "DELETE FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while clearing Persons");
        }
    }

    /**
     * Updates the associatedUsername of all Persons with that associatedUsername
     * @param username username to update
     * @param newUsername new username
     * @return true if the username was updated, false if the User does not exist
     * @throws DaoException if the username could not be updated
     */
    public boolean updateUsername(String username, String newUsername) throws DaoException {
        int r;
        String sql = "UPDATE Person SET associatedUsername = ?  WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating username on Persons");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the fatherID of a Person
     * @param personID Person to update
     * @param fatherID Father ID to update
     * @return true if fatherID was updated, false if the Person or father does not exist
     * @throws DaoException if the fatherID could not be updated
     */
    public boolean updateFather(String personID, String fatherID) throws DaoException {
        int r;
        String sql = "UPDATE Person SET fatherID = ?  WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fatherID);
            stmt.setString(2, personID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating fatherID on Persons");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the motherID of a Person
     * @param personID Person to update
     * @param motherID Mother ID to update
     * @return true if the motherID was updated, false if the Person or mother does not exist
     * @throws DaoException if the motherID could not be updated
     */
    public boolean updateMother(String personID, String motherID) throws DaoException {
        int r;
        String sql = "UPDATE Person SET motherID = ?  WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, motherID);
            stmt.setString(2, personID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating motherID on Persons");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the spouseID of a Person
     * @param personID Person to update
     * @param spouseID Spouse ID to update
     * @return true fi the spouseID was updated, false if the Person or spouse does not exist
     * @throws DaoException if the spouseID could not be updated
     */
    public boolean updateSpouse(String personID, String spouseID) throws  DaoException {
        int r;
        String sql = "UPDATE Person SET spouseID = ?  WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spouseID);
            stmt.setString(2, personID);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating spouseID on Persons");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Clears the Person table
     * @throws DaoException if there is an error while clearing
     */
    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("Error: SQL Error encountered while clearing Person table");
        }
    }
}
