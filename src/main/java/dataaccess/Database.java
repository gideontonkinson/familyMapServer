package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DaoException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DaoException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DaoException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Unable to close database connection");
        }
    }

    public void createTables() throws DaoException {
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS Users;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Person;");
            stmt.executeUpdate("DROP TABLE IF EXISTS Events;");
            stmt.executeUpdate("DROP TABLE IF EXISTS AuthToken;");
            stmt.executeUpdate("CREATE TABLE Users (" +
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
}

