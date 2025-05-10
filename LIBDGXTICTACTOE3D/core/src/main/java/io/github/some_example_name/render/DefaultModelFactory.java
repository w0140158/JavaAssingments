package io.github.some_example_name.render;

import io.github.some_example_name.common.CellState;

public class DefaultModelFactory implements ModelFactory {

    @Override
    public MyModel createModel(CellState state) {
        switch (state) {
            case X:
                return new StaticModel(0, 0, 0, 0, 0, 0).createModel("hash.obj");
            case O:
                return new StaticModel(0, 0, 0, 0, 0, 0).createModel("sphere.obj");
            default:
                return new StaticModel(0, 0, 0, 0, 0, 0).createModel("cube.obj");
        }
    }
}
