package services;

import dataaccess.*;
import requestresult.*;

public class Clear {

    private final Database db;

    /**
     * Creates a Clear Service Object
     */
    public Clear() {
        db = new Database();
    }

    /**
     * Clears the database
     * @return ClearResult if successful
     */
    public ClearResult clear() {
        boolean commit = false;
        ClearResult result;
        try{
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            personDao.clear();
            eventDao.clear();
            userDao.clear();
            authTokenDao.clear();
            result = new ClearResult("Clear succeeded");
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new ClearResult(e.getMessage(), false);
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
