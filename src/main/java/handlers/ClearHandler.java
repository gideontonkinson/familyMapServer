package handlers;

import com.google.gson.Gson;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import requestresult.ClearResult;
import services.Clear;

/**
 * Handles an HTTP Request to clear the database
 */
public class ClearHandler implements Handler {

    /**
     * Handles an HTTP Exchange to clear the information from the database
     * @param exchange HTTP Exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
       try {
           if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
               Clear service = new Clear();
               Gson gson = new Gson();
               ClearResult result = service.clear();
               if(result.isSuccess() == true){
                   exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
               } else {
                   exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
               }
               Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
               gson.toJson(result, resBody);
               resBody.close();
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
