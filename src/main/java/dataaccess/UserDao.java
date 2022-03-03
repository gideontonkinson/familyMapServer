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
            throw new DaoException("Error: Encountered error while finding User");
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
            throw new DaoException("Error: Encountered error while inserting User into the database");
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
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding User");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }

    /**
     * Updates the username of a User
     * @param username
     * @param newUsername
     * @return true if the username was updated, false if the User does not exist
     * @throws DaoException if the username could not be updated
     */
    public boolean updateUsername(String username, String newUsername) throws DaoException {
        int r;
        String sql = "UPDATE Users SET username = ?  WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating username on User");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the password of a User
     * @param username
     * @param password
     * @return true if the password was updated, false if the User does not exist
     * @throws DaoException if the password could not be updated or User does not exist
     */
    public boolean updatePassword(String username, String password) throws DaoException {
        int r;
        String sql = "UPDATE Users SET password = ?  WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, password);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating password on User");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Updates the email of a User
     * @param username
     * @param email
     * @return true if the email was updated, false if the User does not exist
     * @throws DaoException if the email could not be updated or User does not exist
     */
    public boolean updateEmail(String username, String email) throws DaoException {
        int r;
        String sql = "UPDATE Users SET email = ?  WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating password on User");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("Error: SQL Error encountered while clearing Users table");
        }
    }
}
