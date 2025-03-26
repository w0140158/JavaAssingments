package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

public abstract class MyModel {
    protected Vector3 position;
    protected Vector3 rotation;
    public Model model;
    protected ModelInstance instance;

    // Constructor setting initial rotation and position
    public MyModel(float x, float y, float z, float rotX, float rotY, float rotZ) {
        this.position = new Vector3(x, y, z);
        this.rotation = new Vector3(rotX, rotY, rotZ);
    }

    // Abstract method to move the object.
    public abstract void move(float speedX, float speedY, float speedZ);

    // Abstract method to load 3D model
    public abstract void createModel(String modelPath);

    // Model Instance return
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

    // Creates a texture from the given texture.
    public void createTexture(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        if (instance != null) {
            instance.materials.forEach(mat -> mat.set(TextureAttribute.createDiffuse(texture)));
        }
    }

    // Draw method to render the model.
    public void draw(com.badlogic.gdx.graphics.g3d.ModelBatch batch, com.badlogic.gdx.graphics.g3d.Environment env) {
        batch.render(getInstance(), env);
    }
}
