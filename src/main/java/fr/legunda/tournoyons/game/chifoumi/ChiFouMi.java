package fr.legunda.tournoyons.game.chifoumi;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import static fr.legunda.tournoyons.server.core.MapParam.Parameter;
import static fr.legunda.tournoyons.server.core.MapParam.Parameter.GAME;
import static fr.legunda.tournoyons.server.core.MapParam.Parameter.MOVEID;
import static fr.legunda.tournoyons.server.core.MapParam.Parameter.VALUE;

public class ChiFouMi {

    Logger LOGGER = LoggerFactory.getLogger(ChiFouMi.class);

    public final String name = "Rochambeau";

    public final String idGame;

    public final String idOpponent;

    public final String refereeUrl;

    public ChiFouMi(String idGame, String idOpponent, String refereeUrl) {
        this.idGame = idGame;
        this.idOpponent = idOpponent;
        this.refereeUrl = refereeUrl;
    }

    public String play(String moveId) {
        return new StringBuilder(refereeUrl).append("?")
                .append(makeCouple(GAME, idGame)).append("&")
                .append(makeCouple(MOVEID, moveId)).append("&")
                .append(makeCouple(VALUE, getRandomValue()))
                .toString();
    }

    private String makeCouple(Parameter key, String value) {
        return String.format("%s=%s", key, value);
    }

    public String getRandomValue() {
        String result = getRandomValueFromRandomService();

        if (result == null) {
            result = getRandomValueFromJDK();
        }

        return result;
    }

    @VisibleForTesting
    String getRandomValueFromJDK() {
        Random random = new Random();

        //get the range, casting to long to avoid overflow problems
        Long max = 3L;

        // compute a fraction of the range, 0 <= frac < range
        Long fraction = (long) (max * random.nextDouble());

        Integer randomNumber = (int) (fraction + 1);


        return randomNumber.toString();
    }

    @VisibleForTesting
    String getRandomValueFromRandomService() {
        StringBuilder result = new StringBuilder();
        BufferedReader buffer = null;

        try {
            URL url = new URL("http://www.random.org/integers/?num=1&min=1&max=3&col=1&base=10&format=plain&rnd=new");
            URLConnection urlConnection = url.openConnection();

            buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            while ((inputLine = buffer.readLine()) != null) {
                result.append(inputLine);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to call random service.");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close buffer.");
                }
            }
        }

        if (result.length() == 0) {
            return null;
        }

        return result.toString();
    }

}
