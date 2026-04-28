package com.spenderman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.spenderman.ui.manager.ClsSceneManager;

/**
 * Main entry point for Spenderman JavaFX desktop application.
 * Initializes the application window and loads the login screen.
 */
public class ClsSpendermanApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spenderman — Personal Finance");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(860);
        primaryStage.setMinHeight(600);

        // Initialize ClsSceneManager with the primary stage
        ClsSceneManager $sceneManager = ClsSceneManager.getInstance();
        $sceneManager.initialize(primaryStage);

        // Load and display login screen
        $sceneManager.switchTo("login");

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
