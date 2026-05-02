package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsUser;
import com.spenderman.service.ClsUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * Sign up screen controller.
 * UML: ClsSignUpController extends ABaseController implements IObserver
 * Fields: _userService, _firstNameField, _lastNameField, _usernameField, _passwordField, signUpButton, _errorLabel, goLoginLink
 */
public class ClsSignUpController extends ABaseController implements IObserver {

    private ClsUserService _userService;

    @FXML private TextField _firstNameField;
    @FXML private TextField _lastNameField;
    @FXML private TextField _usernameField;
    @FXML private PasswordField _passwordField;
    @FXML private Label _errorLabel;
    @FXML private Label _successLabel;

    // private UserService _userService; // TODO: inject when service layer exists

    public ClsSignUpController(){
        _userService = new ClsUserService();
    }

    @Override
    public void initialize() {
        // FXML auto-calls this after loading
    }

    @FXML
    private void _handleSignUp() {
        String firstName = _firstNameField.getText().trim();
        String lastName = _lastNameField.getText().trim();
        String username = _usernameField.getText().trim();
        String password = _passwordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            _showError("All fields are required.");
            return;
        }

        if (password.length() < 6) {
            _showError("Password must be at least 6 characters.");
            return;
        }

        ClsUser newUser = new ClsUser(0 , firstName , lastName , username);
        newUser.setPasswordHash(password);

        boolean isRegistered = _userService.register(newUser);

        // 3. Handle the boolean result
        if (isRegistered) {
            // Success
            _clearError();
            _showSuccess("✓ Account created! Signing you in…");

            // Simulate brief delay then navigate
            new Thread(() -> {
                try {
                    Thread.sleep(900);
                    javafx.application.Platform.runLater(() -> $sceneManager.switchTo("dashboard"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            // Failure (e.g., username already taken)
            _showError("Registration failed. The username might already be in use.");
            _successLabel.setVisible(false);
            _successLabel.setManaged(false);
        }

//        _clearError();
//        _showSuccess("✓ Account created! Signing you in…");
//
//        // TODO: Call _userService.register()
//        // Simulate brief delay then navigate
//        new Thread(() -> {
//            try {
//                Thread.sleep(900);
//                javafx.application.Platform.runLater(() -> $sceneManager.switchTo("dashboard"));
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }).start();
    }

    @FXML
    private void _handleGoLogin() {
        $sceneManager.switchTo("login");
    }

    private void _showError(String message) {
        _errorLabel.setText("⚠ " + message);
        _errorLabel.setVisible(true);
        _errorLabel.setManaged(true);
        _successLabel.setVisible(false);
        _successLabel.setManaged(false);
    }

    private void _showSuccess(String message) {
        _successLabel.setText(message);
        _successLabel.setVisible(true);
        _successLabel.setManaged(true);
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    private void _clearError() {
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    @Override
    public void refreshData() {
        // No-op for signup screen
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
