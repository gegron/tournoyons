package fr.legunda.tournoyons.game.tictactoc;

import org.junit.Test;

import java.util.Date;

import static fr.legunda.tournoyons.game.tictactoc.Sequence.Player.PLAYER1;
import static fr.legunda.tournoyons.game.tictactoc.Sequence.Player.PLAYER2;
import static org.fest.assertions.Assertions.assertThat;

public class SequenceTest {

    @Test
    public void should_return_player1_winner() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(5);
        sequence.put(1);
        sequence.put(2);
        sequence.put(4);
        sequence.put(8);
        sequence.put(7);
        sequence.put(9);
        sequence.put(6);
        sequence.put(3);

        // When
        Sequence.Player winner = sequence.getWinner();

        // Then
        assertThat(winner).isEqualTo(PLAYER1);
    }

    @Test
    public void should_return_player2_winner() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(5);
        sequence.put(1);
        sequence.put(2);
        sequence.put(4);
        sequence.put(8);
        sequence.put(7);
        sequence.put(9);
        sequence.put(6);
        sequence.put(3);

        // When
        Sequence.Player winner = sequence.getWinner();

        // Then
        assertThat(winner).isEqualTo(PLAYER1);
    }

    @Test
    public void should_return_no_winner() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(5);
        sequence.put(1);
        sequence.put(2);
        sequence.put(8);
        sequence.put(7);
        sequence.put(3);
        sequence.put(6);
        sequence.put(4);
        sequence.put(9);

        // When
        Sequence.Player winner = sequence.getWinner();

        // Then
        assertThat(winner).isNull();
    }

    @Test
    public void should_return_null_if_sequence_ended() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(1, 2, 3, 4, 5 ,6 , 7, 8, 9);

        // When
        Integer result = sequence.selectBestNextMove(PLAYER1);

        // Then
        assertThat(result).isNull();
    }

    @Test
    public void should_return_last_move_to_end_sequence() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(1, 2, 3, 4, 5 ,6 , 7, 8);

        // When
        Integer result = sequence.selectBestNextMove(PLAYER1);

        // Then
        assertThat(result).isEqualTo(9);
    }

    @Test
    public void should_return_win_move_to_win_game() {
        // Given
        Sequence sequence = new Sequence();

        /*
        +---+---+---+
        | X | O |   |
        +---+---+---+
        |   | O | X |
        +---+---+---+
        |   |   |   |
        +---+---+---+
         */
        sequence.put(5, 1, 2, 6);

        // When
        Integer result = sequence.selectBestNextMove(PLAYER1);

        // Then
        assertThat(result).isEqualTo(8);
    }

    @Test
    public void should_return_when_start_game() {
        Date start = new Date();

        // Given
        Sequence sequence = new Sequence();

        // When
        Integer result = sequence.selectBestNextMove(PLAYER1);

        // Then
        assertThat(result).isEqualTo(1);

        Date end = new Date();

        System.out.println(end.getTime() - start.getTime());
    }

    @Test
    public void should_compute_winner() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(1, 2, 3, 5, 4, 8);

        // When
        Sequence.Player result = sequence.getWinner();

        // Then
        assertThat(result).isEqualTo(PLAYER2);
    }


    @Test
    public void should_play() {
        // Given
        Sequence sequence = new Sequence();

        // When
        sequence.put(1);
        sequence.put(2);
        sequence.put(3);
        sequence.put(5);

        Integer bestMove5 = sequence.selectBestNextMove(PLAYER2);
        sequence.put(bestMove5);
        System.out.println(bestMove5);

    }


}
