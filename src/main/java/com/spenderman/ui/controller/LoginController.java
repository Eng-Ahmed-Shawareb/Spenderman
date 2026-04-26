package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Login screen controller.
 * UML: LoginController extends BaseController implements IObserver
 * Fields: userService, usernameField, passwordField, loginButton, errorLabel, goSignUpLink
 */
public class LoginController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    // private UserService userService; // TODO: inject when service layer exists

    @Override
    public void initialize() {
        // FXML auto-calls this after loading
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in both fields.");
            return;
        }

        // TODO: Call userService.login(username, password)
        // For now, proceed to dashboard with dummy user
        clearError();
        sceneManager.switchTo("dashboard");
    }

    @FXML
    private void handleGoSignUp() {
        sceneManager.switchTo("signup");
    }

    private void showError(String message) {
        errorLabel.setText("⚠ " + message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void clearError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @Override
    public void refreshData() {
        // No-op for login screen
    }
}
