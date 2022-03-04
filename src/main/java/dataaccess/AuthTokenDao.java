package dataaccess;

import model.Model;
import model.AuthToken;
import java.sql.*;

/** Data Access Object to interact with the AuthToken table */
public class AuthTokenDao implements Dao{
    /** Connection to the family map database */
    private final Connection conn;

    /**
     * Creates an AuthTokenDao with a connection to a database
     * @param conn Connection to the database
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets an AuthToken from the database
     * @param authCode AuthToken to get from database
     * @return AuthToken created from database data, null if the AuthToken does not exist for User
     * @throws DaoException if data could not be accessed
     */
    @Override
    public AuthToken getFromDB(String authCode) throws DaoException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authCode);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authToken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding AuthToken");
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
     * Gets an AuthToken from the database
     * @param username username to get the AuthToken for
     * @return AuthToken created from database data, null if the AuthToken does not exist for User
     * @throws DaoException if data could not be accessed
     */
    public AuthToken getAuthTokenFromDB(String username) throws DaoException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authToken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while finding AuthToken");
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
     * Adds an AuthToken to the database
     * @param authTokenM AuthToken to add to the database
     * @return true if the AuthToken was added, false if AuthToken already exists for User
     * @throws DaoException if object passed was not an AuthToken or if the AuthToken could not be added
     */
    @Override
    public boolean addToDB(Model authTokenM) throws DaoException {
        AuthToken authToken = (AuthToken)authTokenM;
        String sql = "INSERT INTO AuthToken (authToken, username)" +
                " VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthtoken());
            stmt.setString(2, authToken.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error: Encountered error while inserting AuthToken into the database");
        }
        return true;
    }

    /**
     * Updates the username associated with the AuthToken
     * @param username Username for the AuthToken
     * @param newUsername New Username for the AuthoToken
     * @return true if the username was updated, false if AuthToken does not exist for User
     * @throws DaoException if the username could not be updated
     */
    public boolean updateUsername(String username, String newUsername) throws DaoException {
        int r;
        String sql = "UPDATE AuthToken SET username = ?  WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, username);
            r = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Encountered error while updating username on AuthToken");
        }
        if(r > 0){
            return true;
        }
        return false;
    }

    /**
     * Clears the AuthToken table
     * @throws DaoException if there is an error while clearing
     */
    @Override
    public void clear() throws DaoException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DaoException("Error: SQL Error encountered while clearing AuthToken table");
        }
    }
}
