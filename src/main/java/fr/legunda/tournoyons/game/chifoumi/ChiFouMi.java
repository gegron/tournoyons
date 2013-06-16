package fr.legunda.tournoyons.game.chifoumi;

import com.google.common.annotations.VisibleForTesting;
import fr.legunda.tournoyons.game.Game;
import fr.legunda.tournoyons.util.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLConnection;
import java.util.Random;

import static fr.legunda.tournoyons.server.core.MapParam.Parameter.*;
import static fr.legunda.tournoyons.util.UrlUtils.callUrl;
import static fr.legunda.tournoyons.util.UrlUtils.makeCouple;

public class ChiFouMi extends Game {

    Logger LOGGER = LoggerFactory.getLogger(ChiFouMi.class);

    static {
        name = "Rochambeau";
    }

    public ChiFouMi(String idGame, String idOpponent, String refereeUrl) {
        this.gameId = idGame;
        this.opponent = idOpponent;
        this.refereeUrl = refereeUrl;
    }

    public String play(String moveId) {
        return new StringBuilder(refereeUrl).append("?")
                .append(makeCouple(GAME, gameId)).append("&")
                .append(makeCouple(MOVEID, moveId)).append("&")
                .append(makeCouple(VALUE, getRandomValue()))
                .toString();
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
        URLConnection urlConnection = callUrl("http://www.random.org/integers/?num=1&min=1&max=3&col=1&base=10&format=plain&rnd=new");

        String result = UrlUtils.getContent(urlConnection);

        return result.length() == 0 ? null : result;
    }

}
