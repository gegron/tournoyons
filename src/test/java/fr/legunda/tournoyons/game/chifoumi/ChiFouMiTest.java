package fr.legunda.tournoyons.game.chifoumi;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class ChiFouMiTest {

    @Test
    public void should_generate_random_value_between_1_and_3() {
        // Given
        List<String> result = Lists.newArrayList();
        ChiFouMi chiFouMi = new ChiFouMi(null, null, null);

        // When
        for (int i = 0; i < 100; i++) {
            result.add(chiFouMi.getRandomValueFromJDK());
        }

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result).contains("1");
        assertThat(result).contains("2");
        assertThat(result).contains("3");
        assertThat(result).containsOnly("1", "2", "3");
    }

    @Test
    public void should_play_correct_url() {
        // Given
        String refereeUrl = "http://www.example.com";
        String idGame = "AZERTY";

        ChiFouMi chiFouMiSpy = Mockito.spy(new ChiFouMi(idGame, null, refereeUrl));

        when(chiFouMiSpy.getRandomValue()).thenReturn("2");

        // When
        String moveId = "123";
        String result = chiFouMiSpy.play(moveId);

        // Then
        assertThat(result).isEqualTo("http://www.example.com?GAME=AZERTY&MOVEID=123&VALUE=2");
    }


}
