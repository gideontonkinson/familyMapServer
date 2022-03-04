package handlers;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

/**
 * Sets up a server that can interact with the API
 */
public class Server {
    /** Max waiting connections */
    private static final int MAX_WAITING_CONNECTIONS = 12;
    /** HTTP Server */
    private HttpServer server;

    /**
     * Runs the server found at localhost:{portNumber}
     * @param portNumber Port number to the website
     */
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        System.out.println("Creating contexts");
        server.createContext("/", new FileHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/event", new GetEventHandler());
        server.createContext("/person", new GetPersonHandler());
        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    /**
     * Runs the server at the port number passed in as the first parameter
     * @param args Arguments for the program
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
