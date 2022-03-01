package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;
import model.AuthToken;
import requestresult.GetEventResult;
import requestresult.GetEventsResult;
import requestresult.ResultException;
import services.GetEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class GetEventHandler implements Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                String authToken = reqHeaders.getFirst("Authorization");
                AuthToken token = null;
                Gson gson = new Gson();
                Database db = new Database();
                try{
                    db.openConnection();
                    AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
                    token = authTokenDao.getFromDB(authToken);
                    db.closeConnection(true);
                } catch (DaoException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        db.closeConnection(false);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                }
                if(token == null){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, 0);
                    exchange.getResponseBody().close();
                } else {
                    GetEvent service = new GetEvent();
                    try {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream resBody = exchange.getResponseBody();
                        String urlPath = exchange.getRequestURI().toString();
                        if(urlPath.length() > 7){
                            GetEventResult result = service.getEvent(urlPath.substring(7));
                            gson.toJson(result, (Appendable) resBody);
                        } else {
                            GetEventsResult result = service.getEvents(token);
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
