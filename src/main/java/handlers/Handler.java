package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;
import model.AuthToken;
import java.io.IOException;

/**
 * Handles an HTTP Request
 */
public interface Handler extends HttpHandler {

    /**
     * Checks if a user is authorized
     * @param reqHeaders
     * @return the AuthToken for the user or null if not valid
     * @throws IOException
     */
    default AuthToken authorize(Headers reqHeaders) throws IOException {
        String authToken = reqHeaders.getFirst("Authorization");
        boolean commit = false;
        AuthToken token = null;
        Database db = new Database();
        try {
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            token = authTokenDao.getFromDB(authToken);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
