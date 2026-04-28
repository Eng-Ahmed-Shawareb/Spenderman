package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

/**
 * Login screen controller.
 * UML: ClsLoginController extends ABaseController implements IObserver
 * Fields: _userService, _usernameField, _passwordField, loginButton, _errorLabel, goSignUpLink
 */
public class ClsLoginController extends ABaseController implements IObserver {

    @FXML private TextField _usernameField;
    @FXML private PasswordField _passwordField;
    @FXML private Label _errorLabel;

    // private UserService _userService; // TODO: inject when service layer exists

    @Override
    public void initialize() {
        // FXML auto-calls this after loading
    }

    @FXML
    private void _handleLogin() {
        String username = _usernameField.getText().trim();
        String password = _passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            _showError("Please fill in both fields.");
            return;
        }

        // TODO: Call _userService.login(username, password)
        // For now, proceed to dashboard with dummy user
        _clearError();
        $sceneManager.switchTo("dashboard");
    }

    @FXML
    private void _handleGoSignUp() {
        $sceneManager.switchTo("signup");
    }

    private void _showError(String message) {
        _errorLabel.setText("⚠ " + message);
        _errorLabel.setVisible(true);
        _errorLabel.setManaged(true);
    }

    private void _clearError() {
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    @Override
    public void refreshData() {
        // No-op for login screen
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
