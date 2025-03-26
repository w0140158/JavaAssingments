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

    private MyModel stationary;
    private MyModel moving;
    private MyModel hashObject;

    // Timer for updating positions.
    private float timer = 0f;

    // Camera zoom parameters.
    private float zoomSpeed = 1.0f;
    private float minDistance = 1f;
    private float maxDistance = 80f;

    @Override
    public void create() {
        modelBatch = new ModelBatch();

        // Set up the camera closer to the objects.
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(20.0f, 20.0f, 20.0f);
        camera.lookAt(0, 0, 0);
        camera.near = 0.1f;
        camera.far = 1000f;
        camera.update();

        // Set up the environment with ambient light.
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));

        // Create the stationary object at the center.
        stationary = new StationaryObject(0, 0, 0, 0, 0, 0);
        stationary.createModel("sphere.obj");

        // Increase scale so the object appears larger.
        stationary.getInstance().transform.scl(4.0f);
        setModelColor(stationary.getInstance(), Color.BLUE);

         // Create the first moving object (cube).
        moving = new MovingObject(0, 0, 0, 0, 0, 0);
        moving.createModel("cube.obj");
        moving.getInstance().transform.scl(4.0f);
        setModelColor(moving.getInstance(), Color.RED);
        // Create the second moving object (hash) and scale it larger.
        hashObject = new MovingObject(0, 0, 0, 0, 0, 0);
        hashObject.createModel("hash.obj");
        hashObject.getInstance().transform.scl(5.0f);
        setModelColor(hashObject.getInstance(), Color.GREEN);
    }

    // Helper method to set the diffuse color for all materials.
    private void setModelColor(com.badlogic.gdx.graphics.g3d.ModelInstance instance, Color color) {
        instance.materials.forEach(mat -> mat.set(ColorAttribute.createDiffuse(color)));
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        // Orbit parameters
        float radius = 4.0f;
        float angularSpeed = 1.0f;

        Vector3 center = stationary.position;
        float angle1 = timer * angularSpeed;
        float angle2 = angle1 + (float) Math.PI;

        // Update orbit positions for moving objects.
        float newX1 = center.x + radius * (float) Math.cos(angle1);
        float newZ1 = center.z + radius * (float) Math.sin(angle1);
        moving.position.set(newX1, moving.position.y, newZ1);

        float newX2 = center.x + radius * (float) Math.cos(angle2);
        float newZ2 = center.z + radius * (float) Math.sin(angle2);
        hashObject.position.set(newX2, hashObject.position.y, newZ2);

        // Rotate moving objects.
        moving.rotation.add(1f, 1f, 1f);
        hashObject.rotation.add(1.5f, 1.5f, 1.5f);

        // Camera zoom control.
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) { // Zoom in.
            float targetZ = Math.max(minDistance, camera.position.z - zoomSpeed);
            camera.position.lerp(new Vector3(camera.position.x, camera.position.y, targetZ), 0.1f);
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) { // Zoom out.
            float targetZ = Math.min(maxDistance, camera.position.z + zoomSpeed);
            camera.position.lerp(new Vector3(camera.position.x, camera.position.y, targetZ), 0.1f);
            camera.update();
        }
        Gdx.gl.glClearColor(0.392f, 0.584f, 0.929f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Render the scene using the draw() methods of our models.
        modelBatch.begin(camera);
        stationary.draw(modelBatch, environment);
        moving.draw(modelBatch, environment);
        hashObject.draw(modelBatch, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        stationary.model.dispose();
        moving.model.dispose();
        hashObject.model.dispose();
    }
}
