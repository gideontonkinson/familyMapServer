package handlers;

import com.google.gson.Gson;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import com.sun.net.httpserver.*;
import requestresult.ClearResult;
import requestresult.ResultException;
import services.Clear;


public class ClearHandler implements Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
       try {
           if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
               Clear service = new Clear();
               Gson gson = new Gson();
               try {
                   ClearResult result = service.clear();
                   exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                   OutputStream resBody = exchange.getResponseBody();
                   gson.toJson(result, (Appendable) resBody);
                   resBody.close();
               } catch (ResultException e) {
                   exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                   OutputStream resBody = exchange.getResponseBody();
                   gson.toJson(e, (Appendable) resBody);
                   resBody.close();
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
