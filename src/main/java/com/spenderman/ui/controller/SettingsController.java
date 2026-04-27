package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Settings screen controller.
 * UML: SettingsController extends BaseController implements IObserver
 * Services: userService
 */
public class SettingsController extends BaseController {

    @FXML private Label profileInitials;
    @FXML private Label profileNameLabel;
    @FXML private Label profileSubLabel;
    @FXML private ToggleButton themeToggle;
    @FXML private Label themeStatusLabel;
    @FXML private PasswordField oldPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label passwordFeedback;

    @Override
    public void initialize() {
        // Password match validation
        confirmPasswordField.textProperty().addListener((obs, old, newVal) -> {
            String newPw = newPasswordField.getText();
            if (newVal.isEmpty() || newPw.isEmpty()) {
                passwordFeedback.setVisible(false);
                passwordFeedback.setManaged(false);
                confirmPasswordField.getStyleClass().removeAll("password-match", "password-mismatch");
                return;
            }

            passwordFeedback.setVisible(true);
            passwordFeedback.setManaged(true);

            if (newPw.equals(newVal)) {
                passwordFeedback.setText("✓ Passwords match");
                passwordFeedback.getStyleClass().removeAll("text-red");
                passwordFeedback.getStyleClass().add("text-green");
                confirmPasswordField.getStyleClass().removeAll("password-mismatch");
                confirmPasswordField.getStyleClass().add("password-match");
            } else {
                passwordFeedback.setText("⚠ Passwords do not match");
                passwordFeedback.getStyleClass().removeAll("text-green");
                passwordFeedback.getStyleClass().add("text-red");
                confirmPasswordField.getStyleClass().removeAll("password-match");
                confirmPasswordField.getStyleClass().add("password-mismatch");
            }
        });
    }

    @FXML
    private void handleThemeToggle() {
        boolean isDark = themeToggle.isSelected();
        themeStatusLabel.setText("Currently: " + (isDark ? "Dark Mode" : "Light Mode"));
        // TODO: Call userService.updateTheme(userId, isDark ? ThemeEnum.DARK : ThemeEnum.LIGHT)
        // TODO: Publish "THEME_CHANGED" event via eventBus
    }

    @FXML
    private void handleChangePassword() {
        String oldPw = oldPasswordField.getText();
        String newPw = newPasswordField.getText();
        String confirmPw = confirmPasswordField.getText();

        if (oldPw.isEmpty() || newPw.isEmpty() || confirmPw.isEmpty()) {
            passwordFeedback.setText("⚠ All password fields are required");
            passwordFeedback.getStyleClass().removeAll("text-green");
            passwordFeedback.getStyleClass().add("text-red");
            passwordFeedback.setVisible(true);
            passwordFeedback.setManaged(true);
            return;
        }

        if (!newPw.equals(confirmPw)) {
            return; // Already handled by live validation
        }

        if (newPw.length() < 6) {
            passwordFeedback.setText("⚠ New password must be at least 6 characters");
            passwordFeedback.getStyleClass().removeAll("text-green");
            passwordFeedback.getStyleClass().add("text-red");
            passwordFeedback.setVisible(true);
            passwordFeedback.setManaged(true);
            return;
        }

        // TODO: Call userService.changePassword(userId, oldPw, newPw)
        passwordFeedback.setText("✓ Password updated successfully");
        passwordFeedback.getStyleClass().removeAll("text-red");
        passwordFeedback.getStyleClass().add("text-green");
        passwordFeedback.setVisible(true);
        passwordFeedback.setManaged(true);

        oldPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void handleLogout() {
        sceneManager.logout();
    }

    @Override
    public void refreshData() {
        if (currentUser != null) {
            profileInitials.setText(currentUser.getInitials());
            profileNameLabel.setText(currentUser.getFullName());
            profileSubLabel.setText("@" + currentUser.getUsername() + " · Member since Jan 2025");
        }
    }
}
