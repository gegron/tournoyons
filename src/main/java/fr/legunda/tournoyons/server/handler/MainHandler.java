package fr.legunda.tournoyons.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.legunda.tournoyons.game.chifoumi.ChiFouMi;
import fr.legunda.tournoyons.game.tictactoc.TicTacToc;
import fr.legunda.tournoyons.server.core.HttpParams;
import fr.legunda.tournoyons.server.core.MapParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static fr.legunda.tournoyons.server.core.MapParam.Parameter.*;

public class MainHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainHandler.class);

    private Map<String, ChiFouMi> chiFouMiController = new HashMap<>();

    private Map<String, TicTacToc> ticTacTocController = new HashMap<>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LOGGER.info(String.format("Reception: %s", httpExchange.getRequestURI().toString()));

        MapParam parameters = HttpParams.getParameters(httpExchange.getRequestURI().getQuery());

        // Si jeu existant, je le récupère sinon je le crée
        if (parameters.containsKey(SET)) {
            String set = parameters.get(SET);

            switch (set) {
                case ChiFouMi.name:
                    if(parameters.containsKey(GAME)) {
                        String gameId = parameters.get(GAME);
                        ChiFouMi chiFouMi;

                        if (!chiFouMiController.containsKey(gameId)) {
                            chiFouMiController.put(gameId, new ChiFouMi(gameId, parameters.get(OPPONENT), parameters.get(REFEREE)));
                        }

                        chiFouMi = chiFouMiController.get(gameId);

                        // Fin de la partie
                        if (parameters.containsKey(STATUS)) {
                            Integer status = Integer.valueOf(parameters.get(STATUS));

                            if (status != 0) {
                                chiFouMiController.remove(gameId);

                                LOGGER.info(String.format("End of the chiFouMi game: %s", gameId));
                            }
                            else {
                                String url = chiFouMi.play(parameters.get(MOVEID));
                                callUrl(url);
                            }
                        }
                    }

                    break;
                case TicTacToc.name:
                    if(parameters.containsKey(GAME)) {
                        String gameId = parameters.get(GAME);
                        TicTacToc ticTacToc;

                        if (!ticTacTocController.containsKey(gameId)) {
                            ticTacTocController.put(gameId, new TicTacToc(gameId, parameters.get(OPPONENT), parameters.get(REFEREE), parameters.get(TURN), parameters.get(TRAY)));
                        }

                        ticTacToc = ticTacTocController.get(gameId);

                        // Fin de la partie
                        if (parameters.containsKey(STATUS)) {
                            Integer status = Integer.valueOf(parameters.get(STATUS));

                            if (status != 0) {
                                ticTacTocController.remove(gameId);

                                LOGGER.info(String.format("End of the ticTacToc game: %s", gameId));
                            }
                            else {
                                String url;
                                if(Integer.valueOf(parameters.get(TURN)) == 1) {
                                    url = ticTacToc.playFirst(parameters.get(MOVEID));
                                }
                                else if(Integer.valueOf(parameters.get(TURN)) % 2 == 1) {
                                    url = ticTacToc.play(parameters.get(MOVEID), parameters.get(MOVE2));
                                }
                                else {
                                    url = ticTacToc.play(parameters.get(MOVEID), parameters.get(MOVE1));
                                }

                                callUrl(url);
                            }
                        }
                    }

                    break;
            }
        }

        String msg = "";
        if (parameters.isEmpty()) {
            msg = "TEST";
        }

        byte[] response = msg.getBytes();
        httpExchange.sendResponseHeaders(200, response.length);
        httpExchange.getResponseBody().write(response);
        httpExchange.close();
    }

    private void callUrl(String urlString) {
        StringBuilder result = new StringBuilder();
        BufferedReader buffer = null;

        try {
            LOGGER.info(String.format("Call url: [%s]", urlString));

            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();

            buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            while ((inputLine = buffer.readLine()) != null) {
                result.append(inputLine);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Unable to call url [%s], cause: [%s].", urlString, e.getMessage()));
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close buffer.");
                }
            }
        }
    }

}
