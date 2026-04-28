package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

/**
 * Wallets screen controller.
 * UML: ClsWalletController extends ABaseController implements IObserver
 * Services: walletService
 */
public class ClsWalletController extends ABaseController implements IObserver {

    @FXML private VBox _formPanel;
    @FXML private TextField _nameField;
    @FXML private GridPane _walletGrid;

    @Override
    public void initialize() {
        _loadWallets();
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
    }

    @FXML
    private void _handleAddWallet() {
        // TODO: Call walletService.createWallet(wallet)
        System.out.println("Create wallet: " + _nameField.getText());
        _toggleForm();
        _loadWallets();
    }

    private void _loadWallets() {
        _walletGrid.getChildren().clear();

        // Dummy data matching React prototype
        String[][] wallets = {
                {"Main Bank Account", "12,400.00", "24", "#22C97A"},
                {"Pocket Cash", "850.00", "8", "#4B9EF8"},
                {"Business Account", "45,200.00", "12", "#8875F5"},
        };

        for (int i = 0; i < wallets.length; i++) {
            String[] w = wallets[i];
            VBox card = new VBox(8);
            card.getStyleClass().add("card-padded");

            // Top row: icon + delete button
            HBox top = new HBox();
            top.setSpacing(12);
            top.setAlignment(Pos.CENTER_LEFT);

            Region icon = new Region();
            icon.getStyleClass().add("wallet-icon");
            icon.setStyle("-fx-background-color: " + w[3] + "22;");
            Label iconLabel = new Label("◈");
            iconLabel.setStyle("-fx-text-fill: " + w[3] + "; -fx-font-size: 16px;");
            StackPane iconPane = new StackPane(icon, iconLabel);
            iconPane.setPrefSize(36, 36);

            Pane fill = new Pane();
            HBox.setHgrow(fill, Priority.ALWAYS);

            Button delBtn = new Button("Delete");
            delBtn.getStyleClass().add("btn-danger");

            top.getChildren().addAll(iconPane, fill, delBtn);

            // Wallet name
            Label name = new Label(w[0]);
            name.getStyleClass().add("text-muted");

            // Amount
            Label amount = new Label("EGP " + w[1]);
            amount.getStyleClass().addAll("mono-medium");
            amount.setStyle("-fx-text-fill: " + w[3] + ";");

            // Separator + tx count
            Separator sep = new Separator();
            Label txCount = new Label(w[2] + " transactions");
            txCount.getStyleClass().add("text-muted");
            txCount.setStyle("-fx-font-size: 9px;");

            card.getChildren().addAll(top, name, amount, sep, txCount);

            _walletGrid.add(card, i % 2, i / 2);
            GridPane.setHgrow(card, Priority.ALWAYS);
        }
    }

    @FXML
    private void _handleDeleteWallet() {
        // TODO: Call walletService.deleteWallet(walletId)
    }

    @Override
    public void refreshData() {
        _loadWallets();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
