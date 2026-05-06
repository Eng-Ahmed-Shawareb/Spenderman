package com.spenderman.ui.controller;

import com.spenderman.ui.manager.ClsSceneManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing ClsAppShellController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsAppShellController extends ABaseController {

    @FXML
    private StackPane _contentArea;

    @FXML
    private VBox _navBox;

    @FXML
    private Label _userNameLabel;

    @FXML
    private Label _avatarInitials;

    @FXML
    private ImageView _sidebarLogo;

    @FXML
    private Button _navDashboard;

    @FXML
    private Button _navTransactions;

    @FXML
    private Button _navWallets;

    @FXML
    private Button _navCategories;

    @FXML
    private Button _navCycles;

    @FXML
    private Button _navGoals;

    @FXML
    private Button _navAiChat;

    @FXML
    private Button _navSettings;

    private Map<String, Button> _navButtons;

    private String _activeScreenId = "dashboard";

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
        _navButtons = new HashMap<>();
        _navButtons.put("dashboard", _navDashboard);
        _navButtons.put("transactions", _navTransactions);
        _navButtons.put("wallets", _navWallets);
        _navButtons.put("categories", _navCategories);
        _navButtons.put("cycles", _navCycles);
        _navButtons.put("goals", _navGoals);
        _navButtons.put("ChatView", _navAiChat);
        _navButtons.put("settings", _navSettings);
    }

    /**
     * Method to setContent.
     *
     * @param content the content
     */
    public void setContent(Parent content) {
        _contentArea.getChildren().clear();
        _contentArea.getChildren().add(content);
    }

    /**
     * Method to setActiveNav.
     *
     * @param screenId the screenId
     */
    public void setActiveNav(String screenId) {
        this._activeScreenId = screenId;
        for (Map.Entry<String, Button> entry : _navButtons.entrySet()) {
            Button btn = entry.getValue();
            if (btn == null)
                continue;
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

    /**
     * Method to _navToDashboard.
     */
    @FXML
    private void _navToDashboard() {
        $sceneManager.switchTo("dashboard");
    }

    /**
     * Method to _navToTransactions.
     */
    @FXML
    private void _navToTransactions() {
        $sceneManager.switchTo("transactions");
    }

    /**
     * Method to _navToWallets.
     */
    @FXML
    private void _navToWallets() {
        $sceneManager.switchTo("wallets");
    }

    /**
     * Method to _navToCategories.
     */
    @FXML
    private void _navToCategories() {
        $sceneManager.switchTo("categories");
    }

    /**
     * Method to _navToCycles.
     */
    @FXML
    private void _navToCycles() {
        $sceneManager.switchTo("cycles");
    }

    /**
     * Method to _navToGoals.
     */
    @FXML
    private void _navToGoals() {
        $sceneManager.switchTo("goals");
    }

    /**
     * Method to _onAiChatClick.
     */
    @FXML
    private void _onAiChatClick() {
        $sceneManager.switchTo("ChatView");
    }

    /**
     * Method to _navToSettings.
     */
    @FXML
    private void _navToSettings() {
        $sceneManager.switchTo("settings");
    }

    /**
     * Method to refreshData.
     */
    @Override
    public void refreshData() {
        if ($currentUser != null) {
            _userNameLabel.setText($currentUser.getFirstName());
            _avatarInitials.setText($currentUser.getInitials());
        }
    }

    /**
     * Method to getSidebarLogo.
     *
     * @return the ImageView
     */
    public ImageView getSidebarLogo() {
        return _sidebarLogo;
    }
}
