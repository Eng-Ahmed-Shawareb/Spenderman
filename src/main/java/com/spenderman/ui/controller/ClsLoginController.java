package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsUser;
import com.spenderman.service.ClsTransactionService;
import com.spenderman.service.ClsUserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import com.spenderman.service.ClsUserService;
import java.util.Optional;

/**
 * Class representing ClsLoginController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsLoginController extends ABaseController implements IObserver {

    private ClsUserService _userService;

    @FXML
    private TextField _usernameField;

    @FXML
    private PasswordField _passwordField;

    @FXML
    private Label _errorLabel;

    public ClsLoginController() {
        _userService = new ClsUserService();
    }

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
    }

    /**
     * Method to _handleLogin.
     */
    @FXML
    private void _handleLogin() {
        String username = _usernameField.getText().trim();
        String password = _passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            _showError("Please fill in both fields.");
            return;
        }
        Optional<ClsUser> user = _userService.login(username, password);
        if (user.isPresent()) {
            ClsUser loggedInUser = user.get();
            System.out.println("User logged in successfully: " + loggedInUser.getUsername());
            $sceneManager.setCurrentUser(loggedInUser);
            _clearError();
            $sceneManager.switchTo("dashboard");
        } else {
            _showError("Invalid username or password.");
        }
    }

    /**
     * Method to _handleGoSignUp.
     */
    @FXML
    private void _handleGoSignUp() {
        $sceneManager.switchTo("signup");
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
