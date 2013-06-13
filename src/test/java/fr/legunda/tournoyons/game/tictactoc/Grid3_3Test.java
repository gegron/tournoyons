package fr.legunda.tournoyons.game.tictactoc;

import org.junit.Test;

import static fr.legunda.tournoyons.game.tictactoc.Grid3_3.CellValue.PLAYER1;
import static org.fest.assertions.Assertions.assertThat;

public class Grid3_3Test {

    @Test
    public void should_player1_play_cell_3() {
        // Given
        Grid3_3 grid = new Grid3_3();

        // When
        grid.put(3, PLAYER1);

        // Then
        assertThat(grid.get(0, 2)).isEqualTo(PLAYER1);
    }

    @Test
    public void should_build_coordinate() {
        assertThat(new Grid3_3.Coordinate(1).toString()).isEqualTo("0 0");
        assertThat(new Grid3_3.Coordinate(2).toString()).isEqualTo("0 1");
        assertThat(new Grid3_3.Coordinate(3).toString()).isEqualTo("0 2");
        assertThat(new Grid3_3.Coordinate(4).toString()).isEqualTo("1 0");
        assertThat(new Grid3_3.Coordinate(5).toString()).isEqualTo("1 1");
        assertThat(new Grid3_3.Coordinate(6).toString()).isEqualTo("1 2");
        assertThat(new Grid3_3.Coordinate(7).toString()).isEqualTo("2 0");
        assertThat(new Grid3_3.Coordinate(8).toString()).isEqualTo("2 1");
        assertThat(new Grid3_3.Coordinate(9).toString()).isEqualTo("2 2");
    }

    @Test
    public void should_no_find_witnner() {
        // Given

        // When

        // Then
        //EMPTY
    }


    @Test
    public void should_find_winner_with_diago_line() {
        // Given
        Grid3_3 grid = buildGridWinnerDiagonale();

        // When


        // Then
    }


    @Test
    public void should_find_winner_with_horizontal_line() {
        // Given

        // When

        // Then
    }


    @Test
    public void should_find_winner_with_vertical_line() {
        // Given

        // When

        // Then
    }


    /*
    +---+---+---+
    | 1 | _ | _ |
    +---+---+---+
    | _ | 1 | _ |
    +---+---+---+
    | _ | _ | 1 |
    +---+---+---+
    */
    private Grid3_3 buildGridWinnerDiagonale() {
        Grid3_3 grid = new Grid3_3();

        grid.put(1, PLAYER1);
        grid.put(5, PLAYER1);
        grid.put(9, PLAYER1);

        return grid;
    }

    /*
    +---+---+---+
    | _ | 1 | _ |
    +---+---+---+
    | _ | 1 | _ |
    +---+---+---+
    | _ | 1 | _ |
    +---+---+---+
    */
    private Grid3_3 buildGridWinnerVerti() {
        Grid3_3 grid = new Grid3_3();

        grid.put(2, PLAYER1);
        grid.put(5, PLAYER1);
        grid.put(8, PLAYER1);

        return grid;
    }

    /*
    +---+---+---+
    | _ | _ | _ |
    +---+---+---+
    | _ | _ | _ |
    +---+---+---+
    | 1 | 1 | 1 |
    +---+---+---+
    */
    private Grid3_3 buildGridWinnerHori() {
        Grid3_3 grid = new Grid3_3();

        grid.put(7, PLAYER1);
        grid.put(8, PLAYER1);
        grid.put(9, PLAYER1);

        return grid;
    }

}
