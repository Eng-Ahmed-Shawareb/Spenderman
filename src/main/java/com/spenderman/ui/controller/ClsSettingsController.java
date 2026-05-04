package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.service.ClsUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;

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

    private final ClsUserService userService = new ClsUserService();

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);

        // Live password-match validation while typing
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
                _passwordFeedback.setText("\u2713 Passwords match");
                _passwordFeedback.getStyleClass().removeAll("text-red");
                _passwordFeedback.getStyleClass().add("text-green");
                _confirmPasswordField.getStyleClass().removeAll("password-mismatch");
                _confirmPasswordField.getStyleClass().add("password-match");
            } else {
                _passwordFeedback.setText("\u26a0 Passwords do not match");
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
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.THEME_CHANGED, isDark);
    }

    @FXML
    private void _handleChangePassword() {
        String oldPw = _oldPasswordField.getText();
        String newPw = _newPasswordField.getText();
        String confirmPw = _confirmPasswordField.getText();

        // --- Client-side validation ---
        if (oldPw.isEmpty() || newPw.isEmpty() || confirmPw.isEmpty()) {
            _showPasswordError("\u26a0 All password fields are required.");
            return;
        }

        if (!newPw.equals(confirmPw)) {
            _showPasswordError("\u26a0 Passwords do not match.");
            return;
        }

        if (newPw.length() < 6) {
            _showPasswordError("\u26a0 New password must be at least 6 characters.");
            return;
        }

        if (oldPw.equals(newPw)) {
            _showPasswordError("\u26a0 New password must differ from the current password.");
            return;
        }

        // --- Service call (verifies old password against DB) ---
        boolean success = userService.changePassword($currentUser.getUserID(), oldPw, newPw);

        if (success) {
            // Keep in-memory user in sync so the session stays valid
            $currentUser.setPasswordHash(newPw);

            _passwordFeedback.setText("\u2713 Password updated successfully.");
            _passwordFeedback.getStyleClass().removeAll("text-red");
            _passwordFeedback.getStyleClass().add("text-green");
            _passwordFeedback.setVisible(true);
            _passwordFeedback.setManaged(true);

            _oldPasswordField.clear();
            _newPasswordField.clear();
            _confirmPasswordField.clear();
            _confirmPasswordField.getStyleClass().removeAll("password-match", "password-mismatch");
        } else {
            _showPasswordError("\u26a0 Current password is incorrect.");
        }
    }

    /** Show an error message in the password feedback label. */
    private void _showPasswordError(String message) {
        _passwordFeedback.setText(message);
        _passwordFeedback.getStyleClass().removeAll("text-green");
        _passwordFeedback.getStyleClass().add("text-red");
        _passwordFeedback.setVisible(true);
        _passwordFeedback.setManaged(true);
    }

    @FXML
    private void _handleLogout() {
        $currentUser = null;
        $sceneManager.logout();
    }

    @Override
    public void refreshData() {
        if ($currentUser == null) return;

        _profileInitials.setText($currentUser.getInitials());
        _profileNameLabel.setText($currentUser.getFullName());
        _profileSubLabel.setText("@" + $currentUser.getUsername());

        // Sync the theme toggle to match the current user's stored theme preference
        boolean isDark = !"LIGHT".equalsIgnoreCase($currentUser.getTheme());
        _themeToggle.setSelected(isDark);
        _themeStatusLabel.setText("Currently: " + (isDark ? "Dark Mode" : "Light Mode"));
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        // Settings does not need to react to external data events
    }
}
