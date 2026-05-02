package com.spenderman.ui.controller;

import javafx.scene.layout.Pane;
import com.spenderman.ui.manager.ClsSceneManager;
import com.spenderman.model.ClsUser;

/**
 * Abstract base class for all screen controllers.
 * Matches UML: ABaseController with $currentUser, $eventBus, $sceneManager.
 * All controllers extend this and implement IObserver (when observer layer exists).
 */
public abstract class ABaseController {
    protected ClsSceneManager $sceneManager;
    protected ClsUser $currentUser;
    protected Pane $root;
    // protected AppEventBus $eventBus; // TODO: wire when observer layer is implemented

    public void setSceneManager(ClsSceneManager $sceneManager) {
        this.$sceneManager = $sceneManager;
    }

    public void setCurrentUser(ClsUser user) {
        this.$currentUser = user;
        refreshData();
    }

    public ClsUser getCurrentUser() {
        return $currentUser;
    }

    public Pane getRoot() {
        return $root;
    }

    /**
     * Initialize the controller and build the UI.
     * Called when the screen is about to be displayed.
     */
    public abstract void initialize();

    /**
     * Refresh the UI data. Called when the model notifies of changes.
     */
    public abstract void refreshData();

    /**
     * Observer callback — will be called by AppEventBus.
     * TODO: implement IObserver interface when observer layer exists.
     */
    public void update(String eventType, Object data) {
        // Default no-op; subclasses override as needed
    }
}
