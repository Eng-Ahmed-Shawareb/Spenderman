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
 * Class representing ClsSettingsController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSettingsController extends ABaseController implements IObserver {

    @FXML
    private Label _profileInitials;

    @FXML
    private Label _profileNameLabel;

    @FXML
    private Label _profileSubLabel;

    @FXML
    private ToggleButton _themeToggle;

    @FXML
    private Label _themeStatusLabel;

    @FXML
    private PasswordField _oldPasswordField;

    @FXML
    private PasswordField _newPasswordField;

    @FXML
    private PasswordField _confirmPasswordField;

    @FXML
    private Label _passwordFeedback;

    private final ClsUserService userService = new ClsUserService();

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
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

    /**
     * Method to _handleThemeToggle.
     */
    @FXML
    private void _handleThemeToggle() {
        boolean isDark = _themeToggle.isSelected();
        _themeStatusLabel.setText("Currently: " + (isDark ? "Dark Mode" : "Light Mode"));
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.THEME_CHANGED, isDark);
    }

    /**
     * Method to _handleChangePassword.
     */
    @FXML
    private void _handleChangePassword() {
        String oldPw = _oldPasswordField.getText();
        String newPw = _newPasswordField.getText();
        String confirmPw = _confirmPasswordField.getText();
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
        boolean success = userService.changePassword($currentUser.getUserID(), oldPw, newPw);
        if (success) {
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

    /**
     * Method to _showPasswordError.
     *
     * @param message the message
     */
    private void _showPasswordError(String message) {
        _passwordFeedback.setText(message);
        _passwordFeedback.getStyleClass().removeAll("text-green");
        _passwordFeedback.getStyleClass().add("text-red");
        _passwordFeedback.setVisible(true);
        _passwordFeedback.setManaged(true);
    }

    /**
     * Method to _handleLogout.
     */
    @FXML
    private void _handleLogout() {
        $currentUser = null;
        $sceneManager.logout();
    }

    /**
     * Method to refreshData.
     */
    @Override
    public void refreshData() {
        if ($currentUser == null)
            return;
        _profileInitials.setText($currentUser.getInitials());
        _profileNameLabel.setText($currentUser.getFullName());
        _profileSubLabel.setText("@" + $currentUser.getUsername());
        boolean isDark = !"LIGHT".equalsIgnoreCase($currentUser.getTheme());
        _themeToggle.setSelected(isDark);
        _themeStatusLabel.setText("Currently: " + (isDark ? "Dark Mode" : "Light Mode"));
    }

    /**
     * Method to update.
     *
     * @param evenType the evenType
     * @param data the data
     */
    @Override
    public void update(EnEvenType evenType, Object data) {
    }
}
