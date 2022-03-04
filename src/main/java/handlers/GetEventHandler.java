package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import model.AuthToken;
import requestresult.GetEventResult;
import requestresult.GetEventsResult;
import services.GetEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * Handles an HTTP Request to get an Event
 */
public class GetEventHandler implements Handler {

    /**
     * Handles an HTTP Exchange to get an Event
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    AuthToken token = authorize(reqHeaders);
                    Gson gson = new Gson();
                    GetEvent service = new GetEvent();
                    String urlPath = exchange.getRequestURI().toString();
                    String[] urlParams = urlPath.split("/");
                    Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                    if (urlParams.length == 3) {
                        GetEventResult result = service.getEvent(urlParams[2], token);
                        if(result.getMessage() == null){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        gson.toJson(result, resBody);
                    } else {
                        GetEventsResult result = service.getEvents(token);
                        if(result.getMessage() == null){
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }
                        gson.toJson(result, resBody);
                    }
                    resBody.close();
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, 0);
                    exchange.getResponseBody().close();
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getResponseBody().close();
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
