package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import model.AuthToken;
import requestresult.*;
import services.AddPerson;
import java.io.*;
import java.net.HttpURLConnection;

/**
 * Handles an HTTP Request to add a Person
 */
public class AddPersonHandler implements Handler {

    /**
     * Handles an HTTP Exchange to add a Person
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                AuthToken token = authorize(reqHeaders);
                Gson gson = new Gson();
                AddPerson service = new AddPerson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                AddPersonRequest request = gson.fromJson(reqBody, AddPersonRequest.class);
                AddPersonResult result = service.addPerson(request, token);
                if(result.getMessage() == null){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, 0);
                exchange.getResponseBody().close();
            }
        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            exchange.getResponseBody().close();
        }
    }
}
