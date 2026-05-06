package com.spenderman.ui.controller;

import javafx.scene.layout.Pane;
import com.spenderman.ui.manager.ClsSceneManager;
import com.spenderman.model.ClsUser;

/**
 * Class representing ABaseController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public abstract class ABaseController {

    protected ClsSceneManager $sceneManager;

    protected ClsUser $currentUser;

    protected Pane $root;

    /**
     * Method to setSceneManager.
     *
     * @param $sceneManager the $sceneManager
     */
    public void setSceneManager(ClsSceneManager $sceneManager) {
        this.$sceneManager = $sceneManager;
    }

    /**
     * Method to setCurrentUser.
     *
     * @param user the user
     */
    public void setCurrentUser(ClsUser user) {
        this.$currentUser = user;
        refreshData();
    }

    /**
     * Method to getCurrentUser.
     *
     * @return the ClsUser
     */
    public ClsUser getCurrentUser() {
        return $currentUser;
    }

    /**
     * Method to getRoot.
     *
     * @return the Pane
     */
    public Pane getRoot() {
        return $root;
    }

    /**
     * Method to initialize.
     */
    public abstract void initialize();

    /**
     * Method to refreshData.
     */
    public abstract void refreshData();

    /**
     * Method to update.
     *
     * @param eventType the eventType
     * @param data the data
     */
    public void update(String eventType, Object data) {
    }
}
