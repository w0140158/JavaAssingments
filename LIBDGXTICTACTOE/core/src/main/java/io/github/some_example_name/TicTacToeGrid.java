package io.github.some_example_name;

import com.badlogic.gdx.math.Vector3;


// 3 x 3 grid class
public class TicTacToeGrid {
    private final int rows;
    private final int cols;
    private final float cellSpacing;
    private final Vector3 origin;
    private final CellState[][] cells;


    //Parametrized constructor
    public TicTacToeGrid(int rows, int cols, float cellSpacing, Vector3 origin) {
        this.rows = rows;
        this.cols = cols;
        this.cellSpacing = cellSpacing;
        this.origin = origin;
        cells = new CellState[rows][cols];
        resetGrid();
    }

    // Default constructor for 3x3
    public TicTacToeGrid() {
        this(3, 3, 5f, new Vector3(0, 0, 0));
    }

    // Clears/resets the board to EMPTY
    public void resetGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                cells[r][c] = CellState.EMPTY;
            }
        }
    }

    // Place X or O in a cell
    public boolean placeMark(int row, int col, CellState mark) {
        if (isWithinBounds(row, col) && cells[row][col] == CellState.EMPTY) {
            cells[row][col] = mark;
            return true;
        }
        return false;
    }

    public CellState getCellState(int row, int col) {
        if (!isWithinBounds(row, col)) return null;
        return cells[row][col];
    }

    public Vector3 getCellPosition(int row, int col) {
        float xOffset = (col - (cols - 1) / 2.0f) * cellSpacing;
        float zOffset = (row - (rows - 1) / 2.0f) * cellSpacing;
        return new Vector3(origin.x + xOffset, origin.y, origin.z + zOffset);
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    // Checks if there is a winner
    public CellState checkWinner() {
        // Rows
        for (int r = 0; r < rows; r++) {
            if (cells[r][0] != CellState.EMPTY && cells[r][0] == cells[r][1] && cells[r][1] == cells[r][2]) {
                return cells[r][0];
            }
        }
        // Columns
        for (int c = 0; c < cols; c++) {
            if (cells[0][c] != CellState.EMPTY && cells[0][c] == cells[1][c] && cells[1][c] == cells[2][c]) {
                return cells[0][c];
            }
        }
        // Diagonals
        if (cells[1][1] != CellState.EMPTY) {
            if (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2]) {
                return cells[1][1];
            }
            if (cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0]) {
                return cells[1][1];
            }
        }
        return CellState.EMPTY; // No winner yet
    }

    // Checks if board is full (tie)
    public boolean isBoardFull() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (cells[r][c] == CellState.EMPTY) return false;
            }
        }
        return true;
    }
}
