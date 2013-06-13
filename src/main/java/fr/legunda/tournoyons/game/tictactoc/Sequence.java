package fr.legunda.tournoyons.game.tictactoc;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.collections.ListUtils;

import javax.annotation.Nullable;
import java.util.*;

public class Sequence {

    public final Player firstPlayer = Player.PLAYER1;

    private List<Integer> gameSequence = new ArrayList<>();

    private Player winner;

    private SequenceGenerator sequenceGenerator = new SequenceGenerator();

    private Integer points;

    public Sequence() {
    }

    public Sequence(List<Integer> gameSequence) {
        this.gameSequence.addAll(gameSequence);
    }

    /*
     * Build a sequence since the received tray
     */
    public Sequence(String tray) {
        for (int i = 0; i < tray.length(); i++) {
            if(tray.charAt(i) == '1' || tray.charAt(i) == '2') {
                put(i + 1);
            }
        }
    }

    public Integer selectBestNextMove(Player me) {
        if (this.getGameSequence().size() == 9) {
            return null;
        }

        Sequence bestSequence = selectBestSequenceWithMinMaxAlgo(me);

        return bestSequence.getGameSequence().get(this.getGameSequence().size());
    }

    /**
     * Compute best next move for player
     *
     * @param me
     * @return
     */
    private Sequence selectBestSequenceWithMinMaxAlgo(Player me) {
        if (this.isEnded()) {
            this.computePoint(me);

            return this;
        } else {
            List<Sequence> nextSequences = Lists.newArrayList();

            for (Sequence sequence : sequenceGenerator.generateNextMove(this)) {
                nextSequences.add(sequence.selectBestSequenceWithMinMaxAlgo(me));
            }

            return selectMinMax(nextSequences, me);
        }
    }

    private Sequence selectMinMax(List<Sequence> sequences, Player me) {
        if (this.getLastPlayerToPlayer() == Player.opponent(me)) {
            return selectMax(sequences);
        } else {
            return selectMin(sequences);
        }
    }

    private Sequence selectMin(List<Sequence> sequences) {
        return Collections.min(sequences, new Comparator<Sequence>() {
            @Override
            public int compare(Sequence seq1, Sequence seq2) {
                return seq1.getPoints().compareTo(seq2.getPoints());
            }
        });
    }

    private Sequence selectMax(List<Sequence> sequences) {
        return Collections.max(sequences, new Comparator<Sequence>() {
            @Override
            public int compare(Sequence seq1, Sequence seq2) {
                return seq1.getPoints().compareTo(seq2.getPoints());
            }
        });
    }

    private void computePoint(Player me) {
        if (this.getWinner() == null) {
            this.points = 0;
        } else if (this.getWinner() == me) {
            this.points = 10 - this.gameSequence.size();
        } else {
            this.points = -10 + this.gameSequence.size();
        }
    }

    private boolean isEnded() {
        return this.gameSequence.size() == 9 || this.getWinner() != null;
    }

    /**
     * To get next player, we get size list of gameSequence, if size is even, next player is firstPlayer else it's his opponent.
     */
    private Player getNextPlayerToPlay() {
        if (gameSequence.size() % 2 == 0) {
            return firstPlayer;
        } else {
            return Player.opponent(firstPlayer);
        }
    }

    public Player getWinner() {
        if (winner == null) {
            computeWinner();
        }

        return winner;
    }

    private void computeWinner() {
        int i = 4;

        do {
            List<Integer> cellPlayer1 = retrieveCellOwnedByPlayer(Player.PLAYER1, i);
            List<Integer> cellPlayer2 = retrieveCellOwnedByPlayer(Player.PLAYER2, i);

            for (List<Integer> winSequence : TicTacToc.winSequence) {
                if (ListUtils.intersection(winSequence, cellPlayer1).size() == 3) {
                    winner = Player.PLAYER1;
                }
                if (ListUtils.intersection(winSequence, cellPlayer2).size() == 3) {
                    winner = Player.PLAYER1;
                }
            }
            i++;
        }
        while (i > 9 && winner == null);
    }

    private List<Integer> retrieveCellOwnedByPlayer(Player player, final int turn) {
        final int indexPlayer = (player == firstPlayer) ? 0 : 1;

        Collection<Integer> cells = Collections2.filter(gameSequence, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer cell) {
                if (cell == null) {
                    return false;
                }

                int index = gameSequence.indexOf(cell);

                if (index % 2 == indexPlayer && index <= turn) {
                    return true;
                }

                return false;
            }
        });

        return new ArrayList<>(cells);
    }

    public void putAll(List<Integer> listValue) {
        gameSequence.addAll(listValue);
    }

    public List<Integer> getGameSequence() {
        return gameSequence;
    }

    public void put(int... args) {
        for (int i = 0; i < args.length; i++) {
            if(!gameSequence.contains(args[i])) {
                gameSequence.add(args[i]);
            }
        }
    }

    @Override
    public String toString() {
        return gameSequence.toString();
    }

    public Player getLastPlayerToPlayer() {
        if (gameSequence.size() % 2 == 1) {
            return firstPlayer;
        } else {
            return Player.opponent(firstPlayer);
        }
    }

    public Integer getPoints() {
        return points;
    }

    public enum Player {
        PLAYER1, PLAYER2;

        public static Player opponent(Player player) {
            if (player == PLAYER1) {
                return PLAYER2;
            } else {
                return PLAYER1;
            }
        }
    }
}
