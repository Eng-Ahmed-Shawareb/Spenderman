package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.service.ClsUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import com.spenderman.Observer.Singleton.ClsAppEventBus;

/**
 * Settings screen controller.
 * UML: ClsSettingsController extends ABaseController implements IObserver
 * Services: userService
 */
public class ClsSettingsController extends ABaseController implements IObserver {

    @FXML private Label _profileInitials;
    @FXML private Label _profileNameLabel;
    @FXML private Label _profileSubLabel;
    @FXML private ToggleButton _themeToggle;
    @FXML private Label _themeStatusLabel;
    @FXML private PasswordField _oldPasswordField;
    @FXML private PasswordField _newPasswordField;
    @FXML private PasswordField _confirmPasswordField;
    @FXML private Label _passwordFeedback;

    @Override
    public void initialize() {
        // Password match validation
        _confirmPasswordField.textProperty().addListener((obs, old, newVal) -> {
            String newPw = _newPasswordField.getText();
            if (newVal.isEmpty() || newPw.isEmpty()) {
                _passwordFeedback.setVisible(false);
                _passwordFeedback.setManaged(false);
                _confirmPasswordField.getStyleClass().removeAll("password-match", "password-mismatch");
                return;
            }

            _passwordFeedback.setVisible(true);
            _passwordFeedback.setManaged(true);

            if (newPw.equals(newVal)) {
                _passwordFeedback.setText("✓ Passwords match");
                _passwordFeedback.getStyleClass().removeAll("text-red");
                _passwordFeedback.getStyleClass().add("text-green");
                _confirmPasswordField.getStyleClass().removeAll("password-mismatch");
                _confirmPasswordField.getStyleClass().add("password-match");
            } else {
                _passwordFeedback.setText("⚠ Passwords do not match");
                _passwordFeedback.getStyleClass().removeAll("text-green");
                _passwordFeedback.getStyleClass().add("text-red");
                _confirmPasswordField.getStyleClass().removeAll("password-match");
                _confirmPasswordField.getStyleClass().add("password-mismatch");
            }
        });
    }

    @FXML
    private void _handleThemeToggle() {
        boolean isDark = _themeToggle.isSelected();
        _themeStatusLabel.setText("Currently: " + (isDark ? "Dark Mode" : "Light Mode"));
        // TODO: Call userService.updateTheme(userId, isDark ? ThemeEnum.DARK : ThemeEnum.LIGHT)
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.THEME_CHANGED, isDark);
    }

    @FXML
    private void _handleChangePassword() {
        String oldPw = _oldPasswordField.getText();
        String newPw = _newPasswordField.getText();
        String confirmPw = _confirmPasswordField.getText();

        if (oldPw.isEmpty() || newPw.isEmpty() || confirmPw.isEmpty()) {
            _passwordFeedback.setText("⚠ All password fields are required");
            _passwordFeedback.getStyleClass().removeAll("text-green");
            _passwordFeedback.getStyleClass().add("text-red");
            _passwordFeedback.setVisible(true);
            _passwordFeedback.setManaged(true);
            return;
        }

        if (!newPw.equals(confirmPw)) {
            return; // Already handled by live validation
        }

        if (newPw.length() < 6) {
            _passwordFeedback.setText("⚠ New password must be at least 6 characters");
            _passwordFeedback.getStyleClass().removeAll("text-green");
            _passwordFeedback.getStyleClass().add("text-red");
            _passwordFeedback.setVisible(true);
            _passwordFeedback.setManaged(true);
            return;
        }

        // TODO: Call userService.changePassword(userId, oldPw, newPw)

        _passwordFeedback.setText("✓ Password updated successfully");
        _passwordFeedback.getStyleClass().removeAll("text-red");
        _passwordFeedback.getStyleClass().add("text-green");
        _passwordFeedback.setVisible(true);
        _passwordFeedback.setManaged(true);

        _oldPasswordField.clear();
        _newPasswordField.clear();
        _confirmPasswordField.clear();
    }

    @FXML
    private void _handleLogout() {
        $currentUser=null;
        $sceneManager.logout();

    }

    @Override
    public void refreshData() {
        if ($currentUser != null) {
            _profileInitials.setText($currentUser.getInitials());
            _profileNameLabel.setText($currentUser.getFullName());
            _profileSubLabel.setText("@" + $currentUser.getUsername() + " · Member since Jan 2025");
        }
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
