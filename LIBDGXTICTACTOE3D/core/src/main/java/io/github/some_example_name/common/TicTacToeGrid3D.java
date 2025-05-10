package io.github.some_example_name.common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.render.DefaultModelFactory;
import io.github.some_example_name.render.ModelFactory;
import io.github.some_example_name.render.MyModel;

public class TicTacToeGrid3D {

    private final int size = 3;
    private final CellState[][][] cells;
    private final MyModel[][][] models;

    // Current selection for highlighting.
    private int currentX = 1, currentY = 1, currentZ = 1;

    private final ModelFactory factory;

    public TicTacToeGrid3D() {
        cells = new CellState[size][size][size];
        models = new MyModel[size][size][size];
        factory = new DefaultModelFactory();

        // Initialize grid cells and assign the appropriate model for each state
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (x == 1 && y == 1 && z == 1) {
                        cells[x][y][z] = CellState.X;
                        models[x][y][z] = factory.createModel(CellState.X);
                    } else if (x == 0 && y == 0 && z == 0) {
                        cells[x][y][z] = CellState.O;
                        models[x][y][z] = factory.createModel(CellState.O);
                    } else {
                        cells[x][y][z] = CellState.EMPTY;
                        models[x][y][z] = factory.createModel(CellState.EMPTY);
                    }
                }
            }
        }
    }

    /**
     * Returns the cell state at the specified coordinates.
     */
    public CellState getCellState(int x, int y, int z) {
        return cells[x][y][z];
    }

    /**
     * Attempts to place a mark in the grid.
     * Returns true if successful.
     */
    public boolean placeMark(int x, int y, int z, CellState state) {
        if (cells[x][y][z] == CellState.EMPTY) {
            cells[x][y][z] = state;
            models[x][y][z] = factory.createModel(state); // Update model to match new state
            return true;
        }
        return false;
    }

    /**
     * Resets the grid to all EMPTY.
     */
    public void resetGrid() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    cells[x][y][z] = CellState.EMPTY;
                    models[x][y][z] = factory.createModel(CellState.EMPTY); // Reset model to EMPTY (cube)
                }
            }
        }
    }

    /**
     * Updates the current selection (for highlighting).
     */
    public void setCurrentSelection(int x, int y, int z) {
        currentX = x;
        currentY = y;
        currentZ = z;
    }

    /**
     * Draws the 3D grid with reduced spacing.
     */
    public void draw(ModelBatch batch, Environment env, PerspectiveCamera camera) {
        float spacing = 10f; // Reduced spacing for closer objects
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {

                    float posX = (x - 1) * spacing;
                    float posY = (y - 1) * spacing;
                    float posZ = (z - 1) * spacing;
                    Vector3 cellPos = new Vector3(posX, posY, posZ);

                    MyModel cellModel = models[x][y][z];
                    cellModel.setPosition(cellPos);

                    // Set color based on cell state.
                    CellState state = cells[x][y][z];
                    if (state == CellState.X) {
                        cellModel.setColor(Color.PURPLE);
                    } else if (state == CellState.O) {
                        cellModel.setColor(Color.BLUE);
                    } else {
                        cellModel.setColor(Color.GREEN);
                    }

                    // Highlight the currently selected cell.
                    if (x == currentX && y == currentY && z == currentZ) {
                        cellModel.setColor(Color.WHITE);
                    }

                    // Log cell position (for debugging)
                    com.badlogic.gdx.Gdx.app.log("Grid3D", "Cell (" + x + "," + y + "," + z + ") pos: " + cellPos);

                    batch.render(cellModel.getInstance(), env);
                }
            }
        }
    }

    /**
     * Dispose of resources (models).
     */
    public void dispose() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    if (models[x][y][z] != null) {
                        models[x][y][z].dispose(); // Dispose of the model
                    }
                }
            }
        }
    }
}
