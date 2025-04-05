package io.github.some_example_name.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public abstract class MyModel {
    public Model model;
    protected ModelInstance instance;
    public Vector3 position;
    public Vector3 rotation;

    public MyModel(float x, float y, float z, float rotX, float rotY, float rotZ) {
        position = new Vector3(x, y, z);
        rotation = new Vector3(rotX, rotY, rotZ);
    }

    public abstract MyModel createModel(String modelPath);


    public ModelInstance getInstance() {
        if (instance == null) {
            // Log a warning if needed.
            System.out.println("Warning: ModelInstance is null!");
        }
        return instance;
    }


    public void setPosition(Vector3 pos) {
        if (instance != null) {
            instance.transform.idt(); // Reset transformation
            instance.transform.translate(pos);
            // Optionally, add a scaling factor here if needed.
        }
    }

    /**
     * Sets the diffuse color on all materials.
     */
    public void setColor(Color color) {
        if (instance != null) {
            instance.materials.forEach(mat ->
                mat.set(com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute.createDiffuse(color))
            );
        }
    }

    /**
     * Dispose of the model's resources.
     */
    public void dispose() {
        if (model != null) {
            model.dispose();
        }
    }

    public void scale(float v) {
    }
}
