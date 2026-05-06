package com.spenderman.ui.controller;

import com.spenderman.model.ClsChatMessage;
import com.spenderman.service.ClsAiChatService;
import com.spenderman.ui.component.ClsUIComponents;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class representing ClsChatController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsChatController extends ABaseController {

    @FXML
    private VBox _chatBox;

    @FXML
    private TextField _messageInput;

    @FXML
    private ScrollPane _scrollPane;

    private ClsAiChatService _aiChatService;

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
        _aiChatService = new ClsAiChatService();
        _chatBox.heightProperty().addListener((obs, oldVal, newVal) -> _scrollPane.setVvalue(1.0));
        _addMessageToUI(new ClsChatMessage("Hello! How can I assist you with your finances today?", false));
    }

    /**
     * Method to _handleSendMessage.
     */
    @FXML
    private void _handleSendMessage() {
        String text = _messageInput.getText().trim();
        if (text.isEmpty() || $currentUser == null)
            return;
        _addMessageToUI(new ClsChatMessage(text, true));
        _messageInput.clear();
        _aiChatService.getAiResponse(text, $currentUser.getUserID()).thenAcceptAsync(response -> {
            Platform.runLater(() -> _addMessageToUI(new ClsChatMessage(response, false)));
        });
    }

    /**
     * Method to _addMessageToUI.
     *
     * @param message the message
     */
    private void _addMessageToUI(ClsChatMessage message) {
        HBox container = new HBox();
        container.setPadding(new Insets(5, 10, 5, 10));
        VBox bubble = new VBox(5);
        bubble.setPadding(new Insets(10));
        bubble.setMinWidth(100);
        bubble.setMaxWidth(350);
        bubble.setStyle("-fx-background-radius: 15px;");
        Label content = new Label(message.get_messageText());
        content.setWrapText(true);
        content.setStyle("-fx-font-size: 13px;");
        Label time = new Label(String.valueOf(message.get_timestamp()));
        time.setStyle("-fx-font-size: 9px; -fx-text-fill: #8490A8;");
        bubble.getChildren().addAll(content, time);
        if (message.isFromUser()) {
            container.setAlignment(Pos.CENTER_RIGHT);
            bubble.setStyle(bubble.getStyle() + "-fx-background-color: " + ClsUIComponents.BLUE + ";");
            content.setStyle(content.getStyle() + "-fx-text-fill: white;");
        } else {
            container.setAlignment(Pos.CENTER_LEFT);
            bubble.setStyle(bubble.getStyle() + "-fx-background-color: " + ClsUIComponents.SURF3 + ";");
            content.setStyle(content.getStyle() + "-fx-text-fill: #DCE1EE;");
        }
        container.getChildren().add(bubble);
        _chatBox.getChildren().add(container);
    }

    /**
     * Method to refreshData.
     */
    @Override
    public void refreshData() {
    }
}
