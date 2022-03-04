package handlers;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import com.sun.net.httpserver.*;


/**
 * Handles an HTTP Request to get a file
 */
public class FileHandler implements HttpHandler {

    /**
     * Handles an HTTP Exchange to get a file
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String urlPath = exchange.getRequestURI().toString();
                String filePath;
                if (urlPath == null || urlPath.equals("/")){
                    filePath = "web/index.html";
                } else {
                    filePath = "web" + urlPath;
                }
                File page = new File(filePath);
                if(page.exists()){
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(page.toPath(), respBody);
                    respBody.close();
                } else {
                    page = new File("web/HTML/404.html");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(page.toPath(), respBody);
                    respBody.close();
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
