package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;

/**
 * Transactions screen controller.
 * UML: TransactionController extends BaseController implements IObserver
 * Services: transactionService, walletService, categoryService, goalService
 */
public class TransactionController extends BaseController {

    @FXML private VBox formPanel;
    @FXML private TextField amountField;
    @FXML private ToggleButton expenseToggle;
    @FXML private ToggleButton depositToggle;
    @FXML private ToggleButton targetWallet;
    @FXML private ToggleButton targetGoal;
    @FXML private ComboBox<String> targetCombo;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private DatePicker datePicker;
    @FXML private TextArea noteArea;
    @FXML private VBox transactionRows;

    private ToggleGroup typeGroup;
    private ToggleGroup targetGroup;

    @Override
    public void initialize() {
        // Setup toggle groups
        typeGroup = new ToggleGroup();
        expenseToggle.setToggleGroup(typeGroup);
        depositToggle.setToggleGroup(typeGroup);
        expenseToggle.setSelected(true);

        targetGroup = new ToggleGroup();
        targetWallet.setToggleGroup(targetGroup);
        targetGoal.setToggleGroup(targetGroup);
        targetWallet.setSelected(true);

        // Populate combos with dummy data
        targetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
        categoryCombo.getItems().addAll("Food", "Transport", "Salary", "Utilities", "Entertainment", "Freelance");
        datePicker.setValue(LocalDate.now());

        // When target type changes, update combo options
        targetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            targetCombo.getItems().clear();
            if (newToggle == targetWallet) {
                targetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            } else {
                targetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            }
        });

        loadTransactions();
    }

    @FXML
    private void toggleForm() {
        boolean show = !formPanel.isVisible();
        formPanel.setVisible(show);
        formPanel.setManaged(show);
    }

    @FXML
    private void handleAddTransaction() {
        // TODO: Call transactionService.addTransaction(tx)
        System.out.println("Add transaction: " + amountField.getText());
        toggleForm();
        loadTransactions();
    }

    private void loadTransactions() {
        transactionRows.getChildren().clear();

        // Dummy data matching React prototype
        String[][] rows = {
                {"Lunch at Koshary", "◈ Pocket Cash", "Food", "Expense", "-85", "2025-04-15"},
                {"April Salary", "◈ Main Bank", "Salary", "Deposit", "+8,500", "2025-04-01"},
                {"Uber ride", "◈ Pocket Cash", "Transport", "Expense", "-45", "2025-04-14"},
                {"Laptop fund", "◇ New Laptop", "Savings", "Expense", "-500", "2025-04-10"},
                {"Electricity bill", "◈ Main Bank", "Utilities", "Expense", "-320", "2025-04-05"},
        };

        for (String[] r : rows) {
            HBox row = new HBox();
            row.getStyleClass().add("table-row");
            row.setAlignment(Pos.CENTER_LEFT);

            Label desc = new Label(r[0]);
            desc.getStyleClass().add("table-cell");
            desc.setPrefWidth(180);

            Label target = new Label(r[1]);
            target.getStyleClass().addAll("table-cell", r[1].startsWith("◇") ? "text-pink" : "text-sub");
            target.setPrefWidth(140);

            Label cat = new Label(r[2]);
            cat.getStyleClass().addAll("table-cell", "text-sub");
            cat.setPrefWidth(110);

            Label type = new Label(r[3]);
            type.getStyleClass().addAll(r[3].equals("Deposit") ? "badge-green" : "badge-red");
            type.setPrefWidth(90);

            Label amount = new Label(r[4] + " EGP");
            amount.getStyleClass().addAll("table-cell", "mono", r[4].startsWith("+") ? "text-green" : "text-red");
            amount.setPrefWidth(120);

            Label date = new Label(r[5]);
            date.getStyleClass().addAll("table-cell", "text-muted");
            date.setPrefWidth(100);

            row.getChildren().addAll(desc, target, cat, type, amount, date);
            transactionRows.getChildren().add(row);
        }
    }

    @Override
    public void refreshData() {
        loadTransactions();
    }
}
