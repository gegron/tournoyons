package fr.legunda.tournoyons.game.tictactoc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class SequenceGeneratorTest {

    private SequenceGenerator sequenceGenerator = new SequenceGenerator();

    @Ignore
    @Test
    public void should_generate_future_sequence() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(1, 3);

        // When
        List<Sequence> result = sequenceGenerator.generate(sequence);

        // Then
        for (Sequence sequence1 : result) {
            System.out.println(sequence1);
        }
    }

    @Ignore
    @Test
    public void should_generate_all_next_move() {
        // Given
        Sequence sequence = new Sequence();

        sequence.put(1, 3, 8);

        // When
        List<Sequence> sequences = sequenceGenerator.generateNextMove(sequence);

        // Then
        for (Sequence seq : sequences) {
            System.out.println(seq);
        }
    }


}
