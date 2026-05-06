package com.spenderman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.spenderman.ui.manager.ClsSceneManager;

/**
 * Class representing ClsSpendermanApp.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSpendermanApp extends Application {

    /**
     * Method to start.
     *
     * @param primaryStage the primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Spenderman — Personal Finance");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/utils/darkmodeicon.png")));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(860);
        primaryStage.setMinHeight(600);
        ClsSceneManager $sceneManager = ClsSceneManager.getInstance();
        $sceneManager.initialize(primaryStage);
        $sceneManager.switchTo("login");
        primaryStage.show();
    }

    /**
     * Method to main.
     *
     * @param args the args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
