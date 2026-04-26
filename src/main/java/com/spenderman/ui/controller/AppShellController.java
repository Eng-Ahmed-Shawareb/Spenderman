package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * App shell controller — manages the sidebar and content area.
 * The sidebar navigation buttons switch between content screens.
 */
public class AppShellController extends BaseController {

    @FXML private StackPane contentArea;
    @FXML private VBox navBox;
    @FXML private Label userNameLabel;
    @FXML private Label avatarInitials;

    // Nav buttons
    @FXML private Button navDashboard;
    @FXML private Button navTransactions;
    @FXML private Button navWallets;
    @FXML private Button navCategories;
    @FXML private Button navCycles;
    @FXML private Button navGoals;
    @FXML private Button navSettings;

    private Map<String, Button> navButtons;
    private String activeScreenId = "dashboard";

    @Override
    public void initialize() {
        navButtons = new HashMap<>();
        navButtons.put("dashboard", navDashboard);
        navButtons.put("transactions", navTransactions);
        navButtons.put("wallets", navWallets);
        navButtons.put("categories", navCategories);
        navButtons.put("cycles", navCycles);
        navButtons.put("goals", navGoals);
        navButtons.put("settings", navSettings);
    }

    /**
     * Set the content pane to show a loaded FXML root.
     */
    public void setContent(Parent content) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    /**
     * Update the active navigation button styling.
     */
    public void setActiveNav(String screenId) {
        this.activeScreenId = screenId;
        for (Map.Entry<String, Button> entry : navButtons.entrySet()) {
            Button btn = entry.getValue();
            if (entry.getKey().equals(screenId)) {
                btn.getStyleClass().removeAll("nav-button");
                if (!btn.getStyleClass().contains("nav-button-active")) {
                    btn.getStyleClass().add("nav-button-active");
                }
            } else {
                btn.getStyleClass().removeAll("nav-button-active");
                if (!btn.getStyleClass().contains("nav-button")) {
                    btn.getStyleClass().add("nav-button");
                }
            }
        }
    }

    // ─── Navigation handlers ─────────────────────────────────────────────
    @FXML private void navToDashboard()    { sceneManager.switchTo("dashboard"); }
    @FXML private void navToTransactions() { sceneManager.switchTo("transactions"); }
    @FXML private void navToWallets()      { sceneManager.switchTo("wallets"); }
    @FXML private void navToCategories()   { sceneManager.switchTo("categories"); }
    @FXML private void navToCycles()       { sceneManager.switchTo("cycles"); }
    @FXML private void navToGoals()        { sceneManager.switchTo("goals"); }
    @FXML private void navToSettings()     { sceneManager.switchTo("settings"); }

    @Override
    public void refreshData() {
        // Update user info if available
        if (currentUser != null) {
            userNameLabel.setText(currentUser.getFirstName());
            avatarInitials.setText(currentUser.getInitials());
        }
    }
}
