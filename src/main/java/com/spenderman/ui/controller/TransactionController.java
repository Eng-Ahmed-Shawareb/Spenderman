package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Transactions screen controller.
 * UML: TransactionController extends BaseController implements IObserver
 * Services: transactionService, walletService, categoryService, goalService
 */
public class TransactionController extends BaseController {

    // ── Add-form fields ──
    @FXML private VBox formPanel;
    @FXML private TextField amountField;
    @FXML private ToggleButton expenseToggle;
    @FXML private ToggleButton depositToggle;
    @FXML private ToggleButton targetWallet;
    @FXML private ToggleButton targetGoal;
    @FXML private ComboBox<String> targetCombo;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextArea noteArea;

    // ── Edit-form fields ──
    @FXML private VBox editFormPanel;
    @FXML private TextField editAmountField;
    @FXML private ToggleButton editExpenseToggle;
    @FXML private ToggleButton editDepositToggle;
    @FXML private ToggleButton editTargetWallet;
    @FXML private ToggleButton editTargetGoal;
    @FXML private ComboBox<String> editTargetCombo;
    @FXML private ComboBox<String> editCategoryCombo;
    @FXML private TextArea editNoteArea;

    @FXML private VBox transactionRows;

    private ToggleGroup typeGroup;
    private ToggleGroup targetGroup;
    private ToggleGroup editTypeGroup;
    private ToggleGroup editTargetGroup;

    /** Holds the row data currently being edited (index 0-5 = desc,target,cat,type,amount,date; index 6 = note). */
    private String[] currentEditRow;

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

        // When target type changes, update combo options
        targetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            targetCombo.getItems().clear();
            if (newToggle == targetWallet) {
                targetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            } else {
                targetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            }
        });

        // ── Setup edit-form toggle groups ──
        editTypeGroup = new ToggleGroup();
        editExpenseToggle.setToggleGroup(editTypeGroup);
        editDepositToggle.setToggleGroup(editTypeGroup);

        editTargetGroup = new ToggleGroup();
        editTargetWallet.setToggleGroup(editTargetGroup);
        editTargetGoal.setToggleGroup(editTargetGroup);

        editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
        editCategoryCombo.getItems().addAll("Food", "Transport", "Salary", "Utilities", "Entertainment", "Freelance");

        editTargetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            editTargetCombo.getItems().clear();
            if (newToggle == editTargetWallet) {
                editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            } else {
                editTargetCombo.getItems().addAll("New Laptop", "Summer Vacation");
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
            desc.setPrefWidth(170);

            Label target = new Label(r[1]);
            target.getStyleClass().addAll("table-cell", r[1].startsWith("\u25c7") ? "text-pink" : "text-sub");
            target.setPrefWidth(130);

            Label cat = new Label(r[2]);
            cat.getStyleClass().addAll("table-cell", "text-sub");
            cat.setPrefWidth(100);

            Label type = new Label(r[3]);
            type.getStyleClass().addAll(r[3].equals("Deposit") ? "badge-green" : "badge-red");
            type.setPrefWidth(85);

            Label amount = new Label(r[4] + " EGP");
            amount.getStyleClass().addAll("table-cell", "mono", r[4].startsWith("+") ? "text-green" : "text-red");
            amount.setPrefWidth(110);

            Label date = new Label(r[5]);
            date.getStyleClass().addAll("table-cell", "text-muted");
            date.setPrefWidth(90);

            // ── Action buttons ──
            final String[] rowData = r;
            Button editBtn = new Button("Edit");
            editBtn.getStyleClass().add("btn-action-edit");
            editBtn.setOnAction(e -> handleEditTransaction(rowData));

            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("btn-action-delete");
            deleteBtn.setOnAction(e -> handleDeleteTransaction(rowData));

            HBox actions = new HBox(6, editBtn, deleteBtn);
            actions.setAlignment(Pos.CENTER_LEFT);
            actions.setPrefWidth(120);
            actions.setPadding(new Insets(0, 8, 0, 8));

            row.getChildren().addAll(desc, target, cat, type, amount, date, actions);
            transactionRows.getChildren().add(row);
        }
    }

    @FXML
    private void closeEditForm() {
        editFormPanel.setVisible(false);
        editFormPanel.setManaged(false);
        currentEditRow = null;
    }

    @FXML
    private void handleSaveEdit() {
        if (currentEditRow == null) return;
        // TODO: Call transactionService.updateTransaction(id, updatedData)
        System.out.println("Saved edit for: " + currentEditRow[0]
                + " -> new amount: " + editAmountField.getText());
        closeEditForm();
        loadTransactions();
    }

    @FXML
    private void handleEditTransaction(String[] rowData) {
        currentEditRow = rowData;

        // Close the add-form if it is open
        formPanel.setVisible(false);
        formPanel.setManaged(false);

        // Pre-populate amount (strip leading sign and " EGP" if present)
        String rawAmount = rowData[4].replace("+", "").replace("-", "").trim();
        editAmountField.setText(rawAmount);

        // Pre-select type toggle
        String type = rowData[3];
        if (type.equals("Deposit")) {
            editDepositToggle.setSelected(true);
        } else {
            editExpenseToggle.setSelected(true);
        }

        // Pre-select target toggle and populate combo
        String targetRaw = rowData[1];
        boolean isGoal = targetRaw.startsWith("\u25c7");
        editTargetCombo.getItems().clear();
        if (isGoal) {
            editTargetGoal.setSelected(true);
            editTargetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            String goalName = targetRaw.replace("\u25c7", "").trim();
            editTargetCombo.setValue(goalName);
        } else {
            editTargetWallet.setSelected(true);
            editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            String walletName = targetRaw.replace("\u25c8", "").trim();
            editTargetCombo.setValue(walletName);
        }

        // Pre-select category
        editCategoryCombo.setValue(rowData[2]);

        // Clear note (no note in dummy data)
        editNoteArea.clear();

        // Show the edit form
        editFormPanel.setVisible(true);
        editFormPanel.setManaged(true);
    }

    private void handleDeleteTransaction(String[] rowData) {
        // TODO: Call transactionService.deleteTransaction(id) then refresh
        System.out.println("Delete transaction: " + rowData[0]);
        loadTransactions();
    }

    @Override
    public void refreshData() {
        loadTransactions();
    }
}
