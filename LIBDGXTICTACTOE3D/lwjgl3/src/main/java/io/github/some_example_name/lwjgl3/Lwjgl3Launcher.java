package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.some_example_name.GameApp;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // If you have any platform-specific startup logic, handle it here.
        // Otherwise, just create the application.
        createApplication();
    }

    private static void createApplication() {
        // Create the GameApp instance directly (no cast needed).
        new Lwjgl3Application(new GameApp(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("FlyingObjectsProject");

        // Enable vsync
        configuration.useVsync(true);

        // Limit FPS to the monitor's refresh rate + 1
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        // Increase the window size to something larger than 640x480, e.g., 1280x720
        configuration.setWindowedMode(1280, 720);

        // You can change these icons as needed (they are located in lwjgl3/src/main/resources/).
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

        return configuration;
    }
}
