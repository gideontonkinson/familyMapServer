package services;

import dataaccess.*;
import model.Event;
import model.Person;
import model.User;
import requestresult.LoadResult;
import requestresult.LoginRequest;
import requestresult.LoginResult;
import requestresult.ResultException;

public class Login {
    private final Database db;

    /**
     * Creates a Login Service Object
     */
    public Login() {
        db = new Database();
    }

    /**
     * Services the LoginRequest
     * @param r
     * @return LoginResult if successful
     * @throws ResultException if the request was not a success
     */
    LoginResult login(LoginRequest r) throws ResultException {
        String personID = "";
        String authToken = "";
        boolean commit = false;
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            if(userDao.validate(r.getUsername(), r.getPassword())){
                personID = userDao.getFromDB(r.getUsername()).getPersonID();
                authToken = authTokenDao.getAuthTokenFromDB(r.getUsername()).getAuthtoken();
            }
            else {
                throw new ResultException("Error: Wrong Username or Password");
            }
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

        return new LoginResult(authToken, r.getUsername(), personID);
    }
}
