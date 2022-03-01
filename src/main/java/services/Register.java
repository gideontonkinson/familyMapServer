package services;

import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.UserDao;
import model.AuthToken;
import model.User;
import requestresult.LoginResult;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;
import requestresult.ResultException;

public class Register {
    private final Database db;

    /**
     * Creates a Register Service Object
     */
    public Register() {
        db = new Database();
    }

    /**
     * Creates a new user adds them the database and then generates an authToken for them
     * @param r RegisterRequest
     * @return RegisterResult if successful
     * @throws ResultException if the request was not a success
     */
    RegisterResult register(RegisterRequest r) throws ResultException {
        String personID = "";
        String authCode = "";
        boolean commit = false;
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender());
            userDao.addToDB(u);
            personID = u.getPersonID();
            AuthToken authToken = new AuthToken(r.getUsername());
            authTokenDao.addToDB(authToken);
            authCode = authToken.getAuthtoken();
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

        return new RegisterResult(authCode, r.getUsername(), personID);
    }
}
