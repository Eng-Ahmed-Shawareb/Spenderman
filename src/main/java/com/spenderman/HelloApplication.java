package com.spenderman;

import javafx.application.Application;
import javafx.stage.Stage;
import com.spenderman.ui.manager.SceneManager;

/**
 * Main entry point for the Spenderman JavaFX desktop application.
 * Initializes the primary stage and loads the login screen via SceneManager.
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spenderman — Personal Finance");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(860);
        primaryStage.setMinHeight(600);

        // Initialize SceneManager with the primary stage
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.initialize(primaryStage);

        // Load and display login screen
        sceneManager.switchTo("login");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
