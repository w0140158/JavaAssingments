package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    private ModelBatch modelBatch;
    private PerspectiveCamera camera;
    private Environment environment;

    private TicTacToeGrid grid;

    // We'll hold separate models for X, O, and empty.
    private MyModel xPrototype;
    private MyModel oPrototype;
    private MyModel emptyPrototype;

    // This 2D array holds the actual model instances for each cell.
    private MyModel[][] modelCells;

    // Current player's turn: either X or O.
    private CellState currentPlayer = CellState.X;

    // Cursor position for selecting cells via arrow keys.
    private int currentRow = 0, currentCol = 0;

    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // Camera
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(20f, 20f, 20f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();

        // Ambient lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        // Create the 3x3 grid
        grid = new TicTacToeGrid();

        // Create prototypes for X, O, and Empty
        xPrototype = new StaticModel(0,0,0,0,0,0);
        xPrototype.createModel("X.obj");

        oPrototype = new StaticModel(0,0,0,0,0,0);
        oPrototype.createModel("sphere.obj");

        emptyPrototype = new StaticModel(0,0,0,0,0,0);
        emptyPrototype.createModel("cube.obj");

        // Initialize the 3x3 array
        modelCells = new MyModel[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Create a fresh model for the empty cell
                modelCells[r][c] = createModelForCell(CellState.EMPTY);
            }
        }
    }

    // Helper method to create a new instance
    private MyModel createModelForCell(CellState state) {
        MyModel newModel;
        switch (state) {
            case X:
                newModel = new StaticModel(0,0,0,0,0,0);
                newModel.createModel("hash.obj");
                break;
            case O:
                newModel = new StaticModel(0,0,0,0,0,0);
                newModel.createModel("sphere.obj");
                break;
            default: // EMPTY
                newModel = new StaticModel(0,0,0,0,0,0);
                newModel.createModel("cube.obj");
                break;
        }
        return newModel;
    }

    @Override
    public void render() {
        // Clear screen
        Gdx.gl.glClearColor(0.392f, 0.584f, 0.929f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        handleInput();

        // Draw all 3x3 cells
        modelBatch.begin(camera);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Position the model in the correct 3D location
                Vector3 cellPos = grid.getCellPosition(r, c);
                modelCells[r][c].position.set(cellPos);
                if (r == currentRow && c == currentCol) {
                    modelCells[r][c].getInstance().materials.forEach(mat ->
                        mat.set(ColorAttribute.createDiffuse(Color.RED)));
                } else {
                    CellState cellState = grid.getCellState(r, c);
                    if (cellState == CellState.X) {
                        modelCells[r][c].getInstance().materials.forEach(mat ->
                            mat.set(ColorAttribute.createDiffuse(Color.BLUE)));
                    } else if (cellState == CellState.O) {
                        modelCells[r][c].getInstance().materials.forEach(mat ->
                            mat.set(ColorAttribute.createDiffuse(Color.ORANGE)));
                    } else {
                        modelCells[r][c].getInstance().materials.forEach(mat ->
                            mat.set(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)));
                    }
                }
                // Draw the model
                modelCells[r][c].draw(modelBatch, environment);
            }
        }
        modelBatch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            currentRow = (currentRow + 2) % 3; // move up (W)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            currentRow = (currentRow + 1) % 3; // move down (S)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            currentCol = (currentCol + 2) % 3; // move left (A)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            currentCol = (currentCol + 1) % 3; // move right (D)
        }

        // Press SPACE to place mark (X or O)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (grid.placeMark(currentRow, currentCol, currentPlayer)) {
                // Successfully placed, so create the new model for that cell
                modelCells[currentRow][currentCol] = createModelForCell(currentPlayer);

                // Check for a winner
                CellState winner = grid.checkWinner();
                if (winner != CellState.EMPTY) {
                    Gdx.app.log("TicTacToe", "Winner is: " + winner);
                    resetBoard();
                    return;
                }
                // Check for tie
                if (grid.isBoardFull()) {
                    Gdx.app.log("TicTacToe", "It's a tie!");
                    resetBoard();
                    return;
                }
                // Switch players
                currentPlayer = (currentPlayer == CellState.X) ? CellState.O : CellState.X;
            } else {
                Gdx.app.log("TicTacToe", "Cell already occupied!");
            }
        }
    }


    private void resetBoard() {
        // Clear the grid
        grid.resetGrid();
        // Reset modelCells to empty
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                modelCells[r][c] = createModelForCell(CellState.EMPTY);
            }
        }
        currentPlayer = CellState.X;
        currentRow = 0;
        currentCol = 0;
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        // Dispose all loaded model references
        if (xPrototype.model != null) xPrototype.model.dispose();
        if (oPrototype.model != null) oPrototype.model.dispose();
        if (emptyPrototype.model != null) emptyPrototype.model.dispose();

        // Dispose each cell's model
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (modelCells[r][c] != null && modelCells[r][c].model != null) {
                    modelCells[r][c].model.dispose();
                }
            }
        }
    }
}
