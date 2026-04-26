package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Sign up screen controller.
 * UML: SignUpController extends BaseController implements IObserver
 * Fields: userService, firstNameField, lastNameField, usernameField, passwordField, signUpButton, errorLabel, goLoginLink
 */
public class SignUpController extends BaseController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;

    // private UserService userService; // TODO: inject when service layer exists

    @Override
    public void initialize() {
        // FXML auto-calls this after loading
    }

    @FXML
    private void handleSignUp() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        clearError();
        showSuccess("✓ Account created! Signing you in…");

        // TODO: Call userService.register()
        // Simulate brief delay then navigate
        new Thread(() -> {
            try {
                Thread.sleep(900);
                javafx.application.Platform.runLater(() -> sceneManager.switchTo("dashboard"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @FXML
    private void handleGoLogin() {
        sceneManager.switchTo("login");
    }

    private void showError(String message) {
        errorLabel.setText("⚠ " + message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        successLabel.setVisible(false);
        successLabel.setManaged(false);
    }

    private void showSuccess(String message) {
        successLabel.setText(message);
        successLabel.setVisible(true);
        successLabel.setManaged(true);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private void clearError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @Override
    public void refreshData() {
        // No-op for signup screen
    }
}
