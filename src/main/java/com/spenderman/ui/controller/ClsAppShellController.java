package com.spenderman.ui.controller;

import com.spenderman.ui.manager.ClsSceneManager;
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
public class ClsAppShellController extends ABaseController {

    @FXML private StackPane _contentArea;
    @FXML private VBox _navBox;
    @FXML private Label _userNameLabel;
    @FXML private Label _avatarInitials;

    // Nav buttons
    @FXML private Button _navDashboard;
    @FXML private Button _navTransactions;
    @FXML private Button _navWallets;
    @FXML private Button _navCategories;
    @FXML private Button _navCycles;
    @FXML private Button _navGoals;
    @FXML private Button _navAiChat; // 👈 1. ضفنا تعريف الزرار الجديد هنا
    @FXML private Button _navSettings;

    private Map<String, Button> _navButtons;
    private String _activeScreenId = "dashboard";

    @Override
    public void initialize() {
        _navButtons = new HashMap<>();
        _navButtons.put("dashboard", _navDashboard);
        _navButtons.put("transactions", _navTransactions);
        _navButtons.put("wallets", _navWallets);
        _navButtons.put("categories", _navCategories);
        _navButtons.put("cycles", _navCycles);
        _navButtons.put("goals", _navGoals);
        _navButtons.put("ChatView", _navAiChat); // 👈 2. ضفناه في الماب عشان لونه يتغير لما تدوس عليه
        _navButtons.put("settings", _navSettings);
    }

    /**
     * Set the content pane to show a loaded FXML $root.
     */
    public void setContent(Parent content) {
        _contentArea.getChildren().clear();
        _contentArea.getChildren().add(content);
    }

    /**
     * Update the active navigation button styling.
     */
    public void setActiveNav(String screenId) {
        this._activeScreenId = screenId;
        for (Map.Entry<String, Button> entry : _navButtons.entrySet()) {
            Button btn = entry.getValue();
            if(btn == null) continue; // حماية إضافية لو الزرار مش موجود

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
    @FXML private void _navToDashboard()    { $sceneManager.switchTo("dashboard"); }
    @FXML private void _navToTransactions() { $sceneManager.switchTo("transactions"); }
    @FXML private void _navToWallets()      { $sceneManager.switchTo("wallets"); }
    @FXML private void _navToCategories()   { $sceneManager.switchTo("categories"); }
    @FXML private void _navToCycles()       { $sceneManager.switchTo("cycles"); }
    @FXML private void _navToGoals()        { $sceneManager.switchTo("goals"); }

    // 👈 3. ظبطنا الدالة دي عشان تبقى زي إخواتها بالظبط وتفتح الشاشة
    @FXML private void _onAiChatClick()     { $sceneManager.switchTo("ChatView"); }

    @FXML private void _navToSettings()     { $sceneManager.switchTo("settings"); }

    @Override
    public void refreshData() {
        // Update user info if available
        if ($currentUser != null) {
            _userNameLabel.setText($currentUser.getFirstName());
            _avatarInitials.setText($currentUser.getInitials());
        }
    }
}