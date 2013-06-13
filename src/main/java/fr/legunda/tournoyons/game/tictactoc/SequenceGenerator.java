package fr.legunda.tournoyons.game.tictactoc;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SequenceGenerator {

    private final static List<Integer> allValues = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public List<Sequence> generate(final Sequence existingGame) {
        List<Sequence> generatedSequence = new ArrayList<>();

        Collection<Integer> notUsedValues = new ArrayList<>(Collections2.filter(allValues, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer value) {
                return !existingGame.getGameSequence().contains(value);
            }
        }));

        for (List<Integer> listValue : Collections2.permutations(notUsedValues)) {
            Sequence sequence = new Sequence(existingGame.getGameSequence());
            sequence.putAll(listValue);
            generatedSequence.add(sequence);
        }

        return generatedSequence;
    }

    public List<Sequence> generateNextMove(final Sequence existingGame) {
        List<Sequence> generatedSequence = new ArrayList<>();

        Collection<Integer> notUsedValues = new ArrayList<>(Collections2.filter(allValues, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer value) {
                return !existingGame.getGameSequence().contains(value);
            }
        }));

        for (Integer nextValue : notUsedValues) {
            Sequence sequence = new Sequence(existingGame.getGameSequence());
            sequence.put(nextValue);
            generatedSequence.add(sequence);
        }

        return generatedSequence;
    }

}
