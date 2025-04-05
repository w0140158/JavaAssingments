package io.github.some_example_name.input;

/**
 * Abstracts input handling.
 * (Dependency Inversion: Higher-level modules depend on this abstraction rather than concrete input methods.)
 * (Strategy Pattern: Different input devices can be swapped without changing the game loop.)
 */
public interface InputDevice {
    boolean isUp();
    boolean isDown();
    boolean isLeft();
    boolean isRight();
    boolean isMark();
}
