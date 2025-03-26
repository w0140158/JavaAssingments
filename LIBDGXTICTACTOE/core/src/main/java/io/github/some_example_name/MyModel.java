package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public abstract class MyModel {
    public Model model;
    protected ModelInstance instance;
    public Vector3 position;
    public Vector3 rotation;

    public MyModel(float x, float y, float z, float rotX, float rotY, float rotZ) {
        this.position = new Vector3(x, y, z);
        this.rotation = new Vector3(rotX, rotY, rotZ);
    }

    // For loading the 3D model
    public abstract void createModel(String modelPath);

    // Return the model instance with updated transform
    public ModelInstance getInstance() {
        if (instance != null) {
            instance.transform.idt();
            instance.transform.translate(position);
            instance.transform.rotate(Vector3.X, rotation.x);
            instance.transform.rotate(Vector3.Y, rotation.y);
            instance.transform.rotate(Vector3.Z, rotation.z);
        }
        return instance;
    }

    // Render method
    public void draw(com.badlogic.gdx.graphics.g3d.ModelBatch batch, com.badlogic.gdx.graphics.g3d.Environment env) {
        batch.render(getInstance(), env);
    }
}
