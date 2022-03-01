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
     * @throws ResultException if the request was not a success
     */
    public ClearResult clear() throws ResultException {
        boolean commit = false;
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
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResultException(e.getMessage());
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }

        return new ClearResult("Clear succeeded");
    }
}
