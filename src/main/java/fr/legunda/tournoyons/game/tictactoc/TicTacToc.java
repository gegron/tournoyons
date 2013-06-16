package fr.legunda.tournoyons.game.tictactoc;

import com.google.common.collect.Lists;
import fr.legunda.tournoyons.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static fr.legunda.tournoyons.game.tictactoc.Sequence.Player;
import static fr.legunda.tournoyons.game.tictactoc.Sequence.Player.PLAYER1;
import static fr.legunda.tournoyons.game.tictactoc.Sequence.Player.PLAYER2;
import static fr.legunda.tournoyons.server.core.MapParam.Parameter.*;
import static fr.legunda.tournoyons.util.UrlUtils.makeCouple;

public class TicTacToc extends Game {
    Logger LOGGER = LoggerFactory.getLogger(TicTacToc.class);

    public final static List<List<Integer>> winSequence = new ArrayList<>();

    public final static String name = "TicTacToe";

    public Player me;

    static {
        winSequence.add(Lists.newArrayList(1, 2, 3));
        winSequence.add(Lists.newArrayList(4, 5, 6));
        winSequence.add(Lists.newArrayList(7, 8, 9));
        winSequence.add(Lists.newArrayList(1, 4, 7));
        winSequence.add(Lists.newArrayList(2, 5, 8));
        winSequence.add(Lists.newArrayList(3, 6, 9));
        winSequence.add(Lists.newArrayList(1, 5, 9));
        winSequence.add(Lists.newArrayList(3, 5, 7));
    }

    private final String gameId;
    private final String opponent;
    private final String refereeUrl;
    private final Sequence tray;

    public TicTacToc(String gameId, String opponent, String referee, String turn, String tray) {
        this.gameId = gameId;
        this.opponent = opponent;
        this.refereeUrl = referee;

        if (tray == "Init") {
            this.tray = new Sequence();
        }
        else {
            this.tray = new Sequence(tray);
        }

        if (Integer.valueOf(turn) % 2 == 1) {
            this.me = PLAYER1;
        }
        else {
            this.me = PLAYER2;
        }
    }

    public String playFirst(String moveId) {
        this.tray.put(1);

        return buildUrl(moveId, 1);
    }

    public String play(String moveId, String lastOpponentMove) {
        if (lastOpponentMove != null) {
            this.tray.put(Integer.valueOf(lastOpponentMove));
        }

        Integer nextMove = this.tray.selectBestNextMove(me);

        this.tray.put(nextMove);

        return buildUrl(moveId, nextMove);
    }

    private String buildUrl(String moveId, Integer nextMove) {
        return new StringBuilder(refereeUrl).append("?")
                .append(makeCouple(GAME, gameId)).append("&")
                .append(makeCouple(MOVEID, moveId)).append("&")
                .append(makeCouple(VALUE, nextMove.toString()))
                .toString();
    }

}
