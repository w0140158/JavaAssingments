package io.github.some_example_name.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;

public class StaticModel extends MyModel {

    public StaticModel(float x, float y, float z, float rotX, float rotY, float rotZ) {
        super(x, y, z, rotX, rotY, rotZ);
    }

    @Override
    public MyModel createModel(String modelPath) {
        ObjLoader loader = new ObjLoader();
        FileHandle file = Gdx.files.internal(modelPath);
        model = loader.loadModel(file);
        instance = new com.badlogic.gdx.graphics.g3d.ModelInstance(model);
        Gdx.app.log("StaticModel", "Loaded: " + modelPath);
        return this;
    }
}
