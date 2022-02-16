package dataaccess;

import model.Event;
import model.Model;
import model.Person;
import model.User;

import java.sql.*;

public class UserDao implements Dao {
    private final Connection conn;

    /**
     * Creates an UserDao with a connection to a database
     * @param conn
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets a User from the database
     * @param username
     * @return User created from database data, null if the User does not exist
     * @throws DaoException if data could not be accessed
     */
    @Override
    public User getFromDB(String username) throws DaoException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error encountered while finding User");
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
     * Adds a User to the database
     * @param userM
     * @return true if the User was added to the database, false if User already exists
     * @throws DaoException if object passed was not a User or if the User could not be added
     */
    @Override
    public boolean addToDB(Model userM) throws DaoException {
        User user = (User)userM;
        String sql = "INSERT INTO Users (username, password, email, firstName, lastName, gender, personID)" +
                " VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error encountered while inserting User into the database");
        }
        return true;
    }

    /**
     * Logs a user into their account
     * @param username
     * @param password
     * @return true if the User was found and their password matched,
     * false if the User was not found or password did not match the one in the database
     * @throws DaoException if there was an error in validating
     */
    public boolean validate (String username, String password) throws DaoException {
        return true;
    }

    /**
     * Updates the username of a User
     * @param personID
     * @param username
     * @return true if the username was updated, false if the User does not exist
     * @throws DaoException if the username could not be updated
     */
    public boolean updateUserName(String personID, String username) throws DaoException {
        return true;
    }

    /**
     * Updates the password of a User
     * @param personID
     * @param password
     * @return true if the password was updated, false if the User does not exist
     * @throws DaoException if the password could not be updated or User does not exist
     */
    public boolean updatePassword(String personID, String password) throws DaoException {
        return true;
    }

    /**
     * Updates the email of a User
     * @param personID
     * @param email
     * @return true if the email was updated, false if the User does not exist
     * @throws DaoException if the email could not be updated or User does not exist
     */
    public boolean updateEmail(String personID, String email) throws DaoException {
        return true;
    }

    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("SQL Error encountered while clearing Users table");
        }
    }
}
