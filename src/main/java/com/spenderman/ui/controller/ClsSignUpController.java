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
 * Class representing ClsSignUpController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsSignUpController extends ABaseController implements IObserver {

    private ClsUserService _userService;

    @FXML
    private TextField _firstNameField;

    @FXML
    private TextField _lastNameField;

    @FXML
    private TextField _usernameField;

    @FXML
    private PasswordField _passwordField;

    @FXML
    private Label _errorLabel;

    @FXML
    private Label _successLabel;

    public ClsSignUpController() {
        _userService = new ClsUserService();
    }

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
    }

    /**
     * Method to _handleSignUp.
     */
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
        ClsUser newUser = new ClsUser(0, firstName, lastName, username);
        newUser.setPasswordHash(password);
        boolean isRegistered = _userService.register(newUser);
        if (isRegistered) {
            _clearError();
            _showSuccess("✓ Account created! Signing you in…");
            new Thread(() -> {
                try {
                    Thread.sleep(900);
                    javafx.application.Platform.runLater(() -> {
                        java.util.Optional<ClsUser> loggedInUser = _userService.login(username, password);
                        if (loggedInUser.isPresent()) {
                            $sceneManager.setCurrentUser(loggedInUser.get());
                            $sceneManager.switchTo("dashboard");
                        } else {
                            $sceneManager.switchTo("login");
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            _showError("Registration failed. The username might already be in use.");
            _successLabel.setVisible(false);
            _successLabel.setManaged(false);
        }
    }

    /**
     * Method to _handleGoLogin.
     */
    @FXML
    private void _handleGoLogin() {
        $sceneManager.switchTo("login");
    }

    /**
     * Method to _showError.
     *
     * @param message the message
     */
    private void _showError(String message) {
        _errorLabel.setText("⚠ " + message);
        _errorLabel.setVisible(true);
        _errorLabel.setManaged(true);
        _successLabel.setVisible(false);
        _successLabel.setManaged(false);
    }

    /**
     * Method to _showSuccess.
     *
     * @param message the message
     */
    private void _showSuccess(String message) {
        _successLabel.setText(message);
        _successLabel.setVisible(true);
        _successLabel.setManaged(true);
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    /**
     * Method to _clearError.
     */
    private void _clearError() {
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    /**
     * Method to refreshData.
     */
    @Override
    public void refreshData() {
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
