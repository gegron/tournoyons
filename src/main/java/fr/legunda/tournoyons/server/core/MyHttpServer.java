package fr.legunda.tournoyons.server.core;


import com.sun.net.httpserver.HttpServer;
import fr.legunda.tournoyons.server.handler.MainHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings(value = "unused")
public class MyHttpServer {

    private final static String PORT = System.getenv("PORT");

    /**
     * Cette méthode permet de lancer notre serveur
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Creation d'un serveur HTTP
        Integer port;

        if (PORT == null) {
            port = 8080;
        }
        else {
            port = Integer.valueOf(PORT);

        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new MainHandler());

        //Demarrage du serveur
        server.start();
    }
}