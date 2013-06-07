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

}
