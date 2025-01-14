import com.sun.net.httpserver.HttpServer;
import config.RegisterHttpHandler;
import config.VerifyHttpHandler;

import java.io.*;
import java.net.InetSocketAddress;

public class MyHttpServer {

    public static void main(String[] args) throws IOException {
        // create server on port 8000
        InetSocketAddress address = new InetSocketAddress(8080);
        HttpServer server = HttpServer.create(address, 0);

        // bind handler
        server.createContext("/fa", new RegisterHttpHandler());
        server.setExecutor(null);

        server.createContext("/verify", new VerifyHttpHandler());
        server.setExecutor(null);
        server.start();
    }
}