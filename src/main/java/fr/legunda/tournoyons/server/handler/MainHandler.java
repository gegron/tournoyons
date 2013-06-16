package fr.legunda.tournoyons.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.legunda.tournoyons.game.Game;
import fr.legunda.tournoyons.game.chifoumi.ChiFouMi;
import fr.legunda.tournoyons.game.tictactoc.TicTacToc;
import fr.legunda.tournoyons.server.core.HttpParams;
import fr.legunda.tournoyons.server.core.MapParam;
import fr.legunda.tournoyons.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fr.legunda.tournoyons.server.core.MapParam.Parameter.*;
import static fr.legunda.tournoyons.util.UrlUtils.callUrl;

public class MainHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainHandler.class);

    private Map<String, Game> chiFouMiController = new HashMap<>();

    private Map<String, Game> ticTacTocController = new HashMap<>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LOGGER.info(String.format("Reception: %s", httpExchange.getRequestURI().toString()));

        String msg = "";
        MapParam parameters = HttpParams.getParameters(httpExchange.getRequestURI().getQuery());

        // Si jeu existant, je le récupère sinon je le crée
        if (parameters.containsKey(SET)) {
            String set = parameters.get(SET);

            switch (set) {
                case ChiFouMi.name:
                    chiFouMiHandle(parameters, set);
                    break;
                case TicTacToc.name:
                    ticTacToeHandle(parameters, set);
                    break;
            }
        }
        else {
            msg = "TEST";
        }

        byte[] response = msg.getBytes();

        httpExchange.sendResponseHeaders(200, response.length);
        httpExchange.getResponseBody().write(response);
        httpExchange.close();
    }

    private void ticTacToeHandle(MapParam parameters, String set) {
        if (parameters.containsKey(GAME)) {
            String gameId = parameters.get(GAME);
            TicTacToc ticTacToc;

            if (!ticTacTocController.containsKey(gameId)) {
                ticTacTocController.put(gameId, new TicTacToc(gameId, parameters.get(OPPONENT), parameters.get(REFEREE), parameters.get(TURN), parameters.get(TRAY)));
            }

            ticTacToc = (TicTacToc) ticTacTocController.get(gameId);

            if (parameters.containsKey(STATUS)) {
                Integer status = Integer.valueOf(parameters.get(STATUS));

                if (status != 0) {
                    endOfTheGame(set, gameId, ticTacTocController);
                }
                else {
                    String url;

                    if (Integer.valueOf(parameters.get(TURN)) == 1) {
                        url = ticTacToc.playFirst(parameters.get(MOVEID));
                    }
                    else if (Integer.valueOf(parameters.get(TURN)) % 2 == 1) {
                        url = ticTacToc.play(parameters.get(MOVEID), parameters.get(MOVE2));
                    }
                    else {
                        url = ticTacToc.play(parameters.get(MOVEID), parameters.get(MOVE1));
                    }

                    String result = UrlUtils.getContent(callUrl(url));
                }
            }
        }
    }

    private void chiFouMiHandle(MapParam parameters, String set) {
        if (parameters.containsKey(GAME)) {
            String gameId = parameters.get(GAME);
            ChiFouMi chiFouMi;

            if (!chiFouMiController.containsKey(gameId)) {
                chiFouMiController.put(gameId, new ChiFouMi(gameId, parameters.get(OPPONENT), parameters.get(REFEREE)));
            }

            chiFouMi = (ChiFouMi) chiFouMiController.get(gameId);

            // Fin de la partie
            if (parameters.containsKey(STATUS)) {
                Integer status = Integer.valueOf(parameters.get(STATUS));

                if (status != 0) {
                    endOfTheGame(set, gameId, chiFouMiController);
                }
                else {
                    String url = chiFouMi.play(parameters.get(MOVEID));

                    UrlUtils.getContent(callUrl(url));
                }
            }
        }
    }

    private void endOfTheGame(String set, String gameId, Map<String, Game> controller) {
        controller.remove(gameId);

        LOGGER.info(String.format("End of the %s game: %s", set, gameId));
    }

}
