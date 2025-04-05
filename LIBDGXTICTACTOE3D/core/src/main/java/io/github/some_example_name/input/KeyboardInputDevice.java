package io.github.some_example_name.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Implements InputDevice for keyboard input.
 */
public class KeyboardInputDevice implements InputDevice {

    @Override
    public boolean isUp() {
        return Gdx.input.isKeyJustPressed(Input.Keys.W);
    }

    @Override
    public boolean isDown() {
        return Gdx.input.isKeyJustPressed(Input.Keys.S);
    }

    @Override
    public boolean isLeft() {
        return Gdx.input.isKeyJustPressed(Input.Keys.A);
    }

    @Override
    public boolean isRight() {
        return Gdx.input.isKeyJustPressed(Input.Keys.D);
    }

    @Override
    public boolean isMark() {
        return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }
}
