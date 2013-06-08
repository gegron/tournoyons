package fr.legunda.tournoyons.server.core;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HttpParamsTest {

    @Test
    public void should_write_error_when_unknow_param() {
        // Given
        String query = "unknownParam=foo";

        // When
        MapParam result = HttpParams.getParameters(query);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_build_correct_map_param() {
        // Given
        String correctQuery = "Set=setTest&Gain1=23";

        // When
        MapParam result = HttpParams.getParameters(correctQuery);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(MapParam.Parameter.SET)).isNotNull();
        assertThat(result.get(MapParam.Parameter.SET)).isEqualTo("setTest");
        assertThat(result.get(MapParam.Parameter.GAIN1)).isNotNull();
        assertThat(result.get(MapParam.Parameter.GAIN1)).isEqualTo("23");
    }

    @Test
    public void should_build_correct_map_with_game_shifoumi_query() {
        // Given
        String gameQuery = "Set=Rochambeau&Game=5R69UZ1&MoveId=5O4J3882&Turn=2&Move1=1&Gain2=&TimeOut=15&Referee=http://tournoyons.developpez.com/arbitre/arbitre.php&Status=0&Tray=Init";

        // When
        MapParam result = HttpParams.getParameters(gameQuery);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(MapParam.Parameter.SET)).isEqualTo("Rochambeau");
        assertThat(result.get(MapParam.Parameter.GAME)).isEqualTo("5R69UZ1");
        assertThat(result.get(MapParam.Parameter.MOVEID)).isEqualTo("5O4J3882");
        assertThat(result.get(MapParam.Parameter.TURN)).isEqualTo("2");
        assertThat(result.get(MapParam.Parameter.MOVE1)).isEqualTo("1");
        assertThat(result.get(MapParam.Parameter.GAIN2)).isEqualTo("");
        assertThat(result.get(MapParam.Parameter.TIMEOUT)).isEqualTo("15");
        assertThat(result.get(MapParam.Parameter.REFEREE)).isEqualTo("http://tournoyons.developpez.com/arbitre/arbitre.php");
        assertThat(result.get(MapParam.Parameter.TRAY)).isEqualTo("Init");
    }


}
