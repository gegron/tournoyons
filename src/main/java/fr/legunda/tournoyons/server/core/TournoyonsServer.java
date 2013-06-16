package fr.legunda.tournoyons.server.core;


import com.sun.net.httpserver.HttpServer;
import fr.legunda.tournoyons.server.handler.MainHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings(value = "unused")
public class TournoyonsServer {

    private static final String PORT = System.getenv("PORT");

    private static final int DEFAULT_PORT = 8080;

    /**
     * Server launcher
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(getEnvPortIfExist()), 0);

        server.createContext("/", new MainHandler());
        server.start();
    }

    private static Integer getEnvPortIfExist() {
        return PORT == null ? DEFAULT_PORT : Integer.valueOf(PORT);
    }
}