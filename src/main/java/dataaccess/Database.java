package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/** Database to store the connection */
public class Database {
    /** Connection to the family map database */
    private Connection conn;

    /**
     * Opens the connection to database
     * @return the connection to the database
     * @throws DaoException
     */
    public Connection openConnection() throws DaoException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.db";

            conn = DriverManager.getConnection(CONNECTION_URL);

            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Gets the connection object
     * @return Conn
     * @throws DaoException
     */
    public Connection getConnection() throws DaoException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    /**
     * Closes a connection to the database
     * @param commit boolean detailing if the changes should be commited or rolledback
     * @throws DaoException
     */
    public void closeConnection(boolean commit) throws DaoException {
        try {
            if (commit) {
                conn.commit();
            } else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error: Unable to close database connection");
        }
    }

    /**
     * Creates all the tables for the Family Map Server Database
     * @throws DaoException
     */
    public void createTables() throws DaoException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE Users(" +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "firstName TEXT NOT NULL," +
                    "lastName TEXT NOT NULL," +
                    "gender TEXT NOT NULL," +
                    "personID TEXT NOT NULL UNIQUE" +
                    ");");
            stmt.executeUpdate("CREATE TABLE Person (" +
                    "personID TEXT NOT NULL UNIQUE," +
                    "associatedUsername TEXT NOT NULL," +
                    "firstName TEXT NOT NULL," +
                    "lastName TEXT NOT NULL," +
                    "gender TEXT NOT NULL," +
                    "fatherID TEXT," +
                    "motherID TEXT," +
                    "spouseID  TEXT" +
                    ");");
            stmt.executeUpdate("CREATE TABLE Events (" +
                    "eventID TEXT NOT NULL UNIQUE," +
                    "associatedUsername TEXT NOT NULL," +
                    "personID TEXT NOT NULL," +
                    "latitude REAL NOT NULL," +
                    "longitude REAL NOT NULL," +
                    "country TEXT NOT NULL," +
                    "city TEXT NOT NULL," +
                    "eventType  TEXT NOT NULL," +
                    "year  INTEGER NOT NULL" +
                    ");");
            stmt.executeUpdate("CREATE TABLE AuthToken (" +
                    "    authToken  TEXT NOT NULL UNIQUE," +
                    "    username  TEXT NOT NULL" +
                    ");");
        } catch (SQLException e) {
            throw new DaoException("createTables failed");
        }
    }

    /**
     * Drops the tables in the database and then creates them all again for consistent testing
     * @throws DaoException
     */
    public void createTablesForTesting() throws DaoException {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS Users;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Person;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Events;");
            stmt.executeUpdate("DROP TABLE IF EXISTS AuthToken;");
        } catch (SQLException e) {
            throw new DaoException("Error: Dropping tables failed");
        }
        createTables();
    }
}

