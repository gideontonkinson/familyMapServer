package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;
import model.AuthToken;
import requestresult.GetPersonResult;
import requestresult.GetPersonsResult;
import requestresult.ResultException;
import services.GetPerson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class GetPersonHandler implements Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                String authToken = reqHeaders.getFirst("Authorization");
                AuthToken token = null;
                Gson gson = new Gson();
                try{
                    Database db = new Database();
                    db.openConnection();
                    AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
                    token = authTokenDao.getFromDB(authToken);
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                if(token == null){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, 0);
                    exchange.getResponseBody().close();
                } else {
                    GetPerson service = new GetPerson();
                    try {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream resBody = exchange.getResponseBody();
                        String urlPath = exchange.getRequestURI().toString();
                        if(urlPath.length() > 8){
                            GetPersonResult result = service.getPerson(urlPath.substring(8));
                            gson.toJson(result, (Appendable) resBody);
                        } else {
                            GetPersonsResult result = service.getPersons(token);
                            gson.toJson(result, (Appendable) resBody);
                        }
                        resBody.close();
                    } catch (ResultException e) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                        OutputStream resBody = exchange.getResponseBody();
                        gson.toJson(e, (Appendable) resBody);
                        resBody.close();
                    }
                }
            }
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            exchange.getResponseBody().close();
        }
    }
}
