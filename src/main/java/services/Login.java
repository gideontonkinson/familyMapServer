package services;

import dataaccess.*;
import requestresult.LoginRequest;
import requestresult.LoginResult;

/** Services an API request login a user */
public class Login {
    /** Connection to the family map database */
    private final Database db;

    /**
     * Creates a Login Service Object
     */
    public Login() {
        db = new Database();
    }

    /**
     * Services the Login Request
     * @param r Request
     * @return LoginResult if successful
     */
    public LoginResult login(LoginRequest r) {
        boolean commit = false;
        LoginResult result;
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            if(userDao.validate(r.getUsername(), r.getPassword())){
                String personID = userDao.getFromDB(r.getUsername()).getPersonID();
                String authToken = authTokenDao.getAuthTokenFromDB(r.getUsername()).getAuthtoken();
                result = new LoginResult(authToken, r.getUsername(), personID);
            }
            else {
                result = new LoginResult("Error: Wrong Username or Password", false);
            }
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new LoginResult(e.getMessage(), false);
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
