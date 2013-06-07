package fr.legunda.tournoyons.server.core;


import com.sun.net.httpserver.HttpServer;
import fr.legunda.tournoyons.server.handler.MainHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings(value = "unused")
public class MyHttpServer {

    private final static int PORT = 8080;

    /**
     * Cette méthode permet de lancer notre serveur
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //Creation d'un serveur HTTP
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/", new MainHandler());

        //Demarrage du serveur
        server.start();
    }
}