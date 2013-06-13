package fr.legunda.tournoyons.game.tictactoc;

import com.google.common.annotations.VisibleForTesting;

import static fr.legunda.tournoyons.game.tictactoc.Grid3_3.CellValue.EMPTY;

/*
    +---+---+---+
    | 1 | 2 | 3 |
    +---+---+---+
    | 4 | 5 | 6 |
    +---+---+---+
    | 7 | 8 | 9 |
    +---+---+---+
 */
public class Grid3_3 {
    private final static int NB_ROW = 3;
    private final static int NB_COL = 3;

    private CellValue[][] grid = new CellValue[NB_ROW][NB_COL];

    public void clear() {
        for (CellValue[] cellRow : grid) {
            for (CellValue cellValue : cellRow) {
                cellValue = EMPTY;
            }
        }
    }

    public void put(int cellNumber, CellValue value) {
        Coordinate coordinate = new Coordinate(cellNumber);

        grid[coordinate.row][coordinate.col] = value;
    }

    public CellValue get(int cellNumber) {
        Coordinate coordinate = new Coordinate(cellNumber);

        return get(coordinate.row, coordinate.col);
    }

    @VisibleForTesting
    CellValue get(int row, int col) {
        return grid[row][col];
    }

    static class Coordinate {
        public int row;
        public int col;

        Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Coordinate(final int cellNumber) {
            String cellNumberBase3 = String.format("%02d", Integer.valueOf(Integer.toString(cellNumber - 1, 3)));

            this.row = Integer.valueOf(cellNumberBase3.substring(0, 1));
            this.col = Integer.valueOf(cellNumberBase3.substring(1,2));
        }

        @Override
        public String toString() {
            return String.format("%d %d", this.row, this.col);
        }
    }

    enum CellValue {
        EMPTY, PLAYER1, PLAYER2;
    }

}
