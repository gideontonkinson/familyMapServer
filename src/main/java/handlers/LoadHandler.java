package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;
import model.AuthToken;
import requestresult.*;
import services.Load;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler implements Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    AuthToken token = null;
                    Gson gson = new Gson();
                    try {
                        Database db = new Database();
                        db.openConnection();
                        AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
                        token = authTokenDao.getFromDB(authToken);
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    if (token == null) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, 0);
                        exchange.getResponseBody().close();
                    } else {
                        Load service = new Load();
                        InputStream reqBody = exchange.getRequestBody();
                        String reqData = readString(reqBody);
                        LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
                        try {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            OutputStream resBody = exchange.getResponseBody();
                            LoadResult result = service.load(request);
                            gson.toJson(result, (Appendable) resBody);
                            resBody.close();
                        } catch (ResultException e) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                            OutputStream resBody = exchange.getResponseBody();
                            gson.toJson(e, (Appendable) resBody);
                            resBody.close();
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
