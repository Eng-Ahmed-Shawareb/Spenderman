package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.ClsWallet;
import com.spenderman.service.ClsTransactionService;
import com.spenderman.service.ClsWalletService;
import java.util.List;
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
    @FXML private Label _errorLabel;

    private ClsWalletService _walletService;
    private ClsTransactionService _transactionService;

    public ClsWalletController() {
        _walletService = new ClsWalletService();
        _transactionService = new ClsTransactionService();
    }

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
        if ($currentUser != null) {
            refreshData();
        }
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    @FXML
    private void _handleAddWallet() {
        if (_nameField.getText().trim().isEmpty()) {
            _errorLabel.setText("⚠ Please enter a wallet name.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }

        ClsWallet wallet = new ClsWallet(0, $currentUser.getUserID(), _nameField.getText().trim(), 0.0);
        _walletService.createWallet(wallet);
        
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.WALLET_ADDED, wallet);
        
        _nameField.clear();
        _toggleForm();
        _loadWallets();
    }

    private void _loadWallets() {
        _walletGrid.getChildren().clear();
        if ($currentUser == null) return;

        List<ClsWallet> wallets = _walletService.getByUser($currentUser.getUserID());
        List<ClsTransaction> allTxs = _transactionService.getByUser($currentUser.getUserID());

        String[] colors = {"#22C97A", "#4B9EF8", "#8875F5", "#F5A623", "#F472B6"};

        for (int i = 0; i < wallets.size(); i++) {
            ClsWallet w = wallets.get(i);
            String color = colors[i % colors.length];
            
            long txCount = allTxs.stream().filter(tx -> tx.get_walletID() == w.get_walletID()).count();

            VBox card = new VBox(8);
            card.getStyleClass().add("card-padded");

            // Top row: icon + delete button
            HBox top = new HBox();
            top.setSpacing(12);
            top.setAlignment(Pos.CENTER_LEFT);

            Region icon = new Region();
            icon.getStyleClass().add("wallet-icon");
            icon.setStyle("-fx-background-color: " + color + "22;");
            Label iconLabel = new Label("◈");
            iconLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 16px;");
            StackPane iconPane = new StackPane(icon, iconLabel);
            iconPane.setPrefSize(36, 36);

            Pane fill = new Pane();
            HBox.setHgrow(fill, Priority.ALWAYS);

            Button delBtn = new Button("Delete");
            delBtn.getStyleClass().add("btn-danger");
            delBtn.setOnAction(e -> _handleDeleteWallet(w));

            top.getChildren().addAll(iconPane, fill, delBtn);

            // Wallet name
            Label name = new Label(w.get_name());
            name.getStyleClass().add("text-muted");

            // Amount
            Label amount = new Label("EGP " + w.get_balance());
            amount.getStyleClass().addAll("mono-medium");
            amount.setStyle("-fx-text-fill: " + color + ";");

            // Separator + tx count
            Separator sep = new Separator();
            Label txLabel = new Label(txCount + " transactions");
            txLabel.getStyleClass().add("text-muted");
            txLabel.setStyle("-fx-font-size: 9px;");

            card.getChildren().addAll(top, name, amount, sep, txLabel);

            _walletGrid.add(card, i % 2, i / 2);
            GridPane.setHgrow(card, Priority.ALWAYS);
        }
    }

    private void _handleDeleteWallet(ClsWallet w) {
        _walletService.deleteWallet(w.get_walletID());
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.WALLET_DELETED, w);
        _loadWallets();
    }

    @Override
    public void refreshData() {
        _loadWallets();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.WALLET_ADDED || evenType == EnEvenType.WALLET_DELETED || 
            evenType == EnEvenType.TRANSACTION_ADDED || evenType == EnEvenType.TRANSACTION_UPDATED || evenType == EnEvenType.TRANSACTION_DELETED) {
            refreshData();
        }
    }
}
