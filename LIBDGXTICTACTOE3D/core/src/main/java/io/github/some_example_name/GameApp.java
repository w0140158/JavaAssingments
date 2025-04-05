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
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import io.github.some_example_name.common.CellState;
import io.github.some_example_name.common.TicTacToeGrid3D;
import io.github.some_example_name.render.MyModel;
import io.github.some_example_name.render.StaticModel;

/**
 * This is the main class for the 3D Tic Tac Toe game.
 *
 * - The game has a 3x3x3 grid of cells.
 * - The player can move around and select a cell to place a mark.
 * - There are two players: X and O, with the X player starting first.
 * - The camera is positioned closer to the grid for a more intimate view.
 * - The grid cells are large (scaled by 60) and have different colors for each state (X = Purple, O = Blue, Empty = Green).
 * - The user can navigate the grid with W, A, S, D for movement, and PAGE_UP/PAGE_DOWN for vertical navigation.
 * - SPACE is used to place a mark in the selected cell.
 */
public class GameApp extends ApplicationAdapter {
    private ModelBatch modelBatch;
    private PerspectiveCamera camera;
    private Environment environment;

    // The 3D Tic Tac Toe grid.
    private TicTacToeGrid3D grid;

    // 3D array to hold model instances for each cell.
    private MyModel[][][] modelCells;

    // Current player's turn.
    private CellState currentPlayer = CellState.X;

    // Current selection coordinates.
    private int currentX = 1, currentY = 1, currentZ = 1;

    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // Setup camera with closer zoom and wider field of view.
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(40f, 40f, 40f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 0.1f;
        camera.far = 500f;
        camera.update();

        // Setup environment with strong ambient light and directional light.
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));

        // Adding directional light (Red)
        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.set(Color.RED, 1f, -1f, -1f);
        environment.add(directionalLight);

        // Initialize the 3D grid.
        grid = new TicTacToeGrid3D();

        // Create the 3x3x3 model array.
        modelCells = new MyModel[3][3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    modelCells[x][y][z] = createModelForCell(grid.getCellState(x, y, z));
                }
            }
        }
    }

    // Helper method to create a new model instance for a cell.
    private MyModel createModelForCell(CellState state) {
        MyModel newModel;
        switch (state) {
            case X:
                newModel = new StaticModel(0, 0, 0, 0, 0, 0);
                newModel.createModel("hash.obj");
                newModel.scale(60f); // Increase the size of the models further
                newModel.setColor(Color.PURPLE); // Purple color for X
                break;
            case O:
                newModel = new StaticModel(0, 0, 0, 0, 0, 0);
                newModel.createModel("sphere.obj");
                newModel.scale(60f); // Increase the size of the models 2x (from 15f to 30f)
                newModel.setColor(Color.BLUE); // Blue color for O
                break;
            default: // EMPTY
                newModel = new StaticModel(0, 0, 0, 0, 0, 0);
                newModel.createModel("cube.obj");
                newModel.scale(60f); // Increase the size of the models 2x (from 15f to 30f)
                newModel.setColor(Color.GREEN); // Green color for Empty
                break;
        }
        return newModel;
    }

    @Override
    public void render() {
        // Clear screen to cornflower blue.
        Gdx.gl.glClearColor(100f / 255f, 149f / 255f, 237f / 255f, 1f); // Cornflower Blue
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        handleInput();
        // Render the 3D grid.
        modelBatch.begin(camera);
        grid.draw(modelBatch, environment, camera);
        modelBatch.end();
    }

    private void handleInput() {
        // Update selection based on input
        if (Gdx.input.isKeyJustPressed(Input.Keys.W))
            currentY = (currentY + 2) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.S))
            currentY = (currentY + 1) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.A))
            currentX = (currentX + 2) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            currentX = (currentX + 1) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP))
            currentZ = (currentZ + 1) % 3;
        if (Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN))
            currentZ = (currentZ + 2) % 3;

        grid.setCurrentSelection(currentX, currentY, currentZ);

        // Place a mark with SPACE.
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (grid.placeMark(currentX, currentY, currentZ, currentPlayer)) {
                modelCells[currentX][currentY][currentZ] = createModelForCell(currentPlayer);
                currentPlayer = (currentPlayer == CellState.X) ? CellState.O : CellState.X;
            } else {
                Gdx.app.log("TicTacToe", "Cell already occupied!");
            }
        }
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    if (modelCells[x][y][z] != null && modelCells[x][y][z].model != null) {
                        modelCells[x][y][z].model.dispose();
                    }
                }
            }
        }
    }
}
