package io.github.some_example_name.render;

import io.github.some_example_name.common.CellState;

public interface ModelFactory {
    MyModel createModel(CellState state);
}
