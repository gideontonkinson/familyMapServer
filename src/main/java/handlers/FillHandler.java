package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import requestresult.FillResult;
import services.Fill;
import java.io.*;
import java.net.HttpURLConnection;

/**
 * Handles an HTTP Request to fill data for a user
 */
public class FillHandler implements Handler{

    /**
     * Handles an HTTP Exchange to fill fo-data for a user
     * @param exchange HTTP Exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Fill service = new Fill();
                Gson gson = new Gson();
                String urlPath = exchange.getRequestURI().toString();
                String[] urlParams = urlPath.split("/");
                FillResult result;
                if(urlParams.length == 3){
                    result = service.fill(urlParams[2],4);
                } else {
                    result = service.fill(urlParams[2], Integer.parseInt(urlParams[3]));
                }
                if(result.isSuccess()){
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
