package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

/**
 * Transactions screen controller.
 * UML: ClsTransactionController extends ABaseController implements IObserver
 * Services: transactionService, walletService, categoryService, goalService
 */
public class ClsTransactionController extends ABaseController {

    // ── Add-form fields ──
    @FXML private VBox _formPanel;
    @FXML private TextField _amountField;
    @FXML private ToggleButton _expenseToggle;
    @FXML private ToggleButton _depositToggle;
    @FXML private ToggleButton _targetWallet;
    @FXML private ToggleButton _targetGoal;
    @FXML private ComboBox<String> _targetCombo;
    @FXML private ComboBox<String> _categoryCombo;
    @FXML private TextArea _noteArea;

    // ── Edit-form fields ──
    @FXML private VBox _editFormPanel;
    @FXML private TextField _editAmountField;
    @FXML private ToggleButton _editExpenseToggle;
    @FXML private ToggleButton _editDepositToggle;
    @FXML private ToggleButton _editTargetWallet;
    @FXML private ToggleButton _editTargetGoal;
    @FXML private ComboBox<String> _editTargetCombo;
    @FXML private ComboBox<String> _editCategoryCombo;
    @FXML private TextArea _editNoteArea;

    @FXML private VBox _transactionRows;

    private ToggleGroup _typeGroup;
    private ToggleGroup _targetGroup;
    private ToggleGroup _editTypeGroup;
    private ToggleGroup _editTargetGroup;

    /** Holds the row data currently being edited (index 0-5 = desc,target,cat,type,amount,date; index 6 = note). */
    private String[] _currentEditRow;

    @Override
    public void initialize() {
        // Setup toggle groups
        _typeGroup = new ToggleGroup();
        _expenseToggle.setToggleGroup(_typeGroup);
        _depositToggle.setToggleGroup(_typeGroup);

        _targetGroup = new ToggleGroup();
        _targetWallet.setToggleGroup(_targetGroup);
        _targetGoal.setToggleGroup(_targetGroup);
        _targetWallet.setSelected(true);

        _typeGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            if (newToggle != null) {
                boolean isExpense = (newToggle == _expenseToggle);
                _targetGoal.setVisible(!isExpense);
                _targetGoal.setManaged(!isExpense);
                if (isExpense && _targetGroup.getSelectedToggle() == _targetGoal) {
                    _targetWallet.setSelected(true);
                }
            }
        });

        _expenseToggle.setSelected(true);
        _targetGoal.setVisible(false);
        _targetGoal.setManaged(false);

        // Populate combos with dummy data
        _targetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
        _categoryCombo.getItems().addAll("Food", "Transport", "Salary", "Utilities", "Entertainment", "Freelance");

        // When target type changes, update combo options
        _targetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            _targetCombo.getItems().clear();
            if (newToggle == _targetWallet) {
                _targetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            } else {
                _targetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            }
        });

        // ── Setup edit-form toggle groups ──
        _editTypeGroup = new ToggleGroup();
        _editExpenseToggle.setToggleGroup(_editTypeGroup);
        _editDepositToggle.setToggleGroup(_editTypeGroup);

        _editTargetGroup = new ToggleGroup();
        _editTargetWallet.setToggleGroup(_editTargetGroup);
        _editTargetGoal.setToggleGroup(_editTargetGroup);

        _editTypeGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            if (newToggle != null) {
                boolean isExpense = (newToggle == _editExpenseToggle);
                _editTargetGoal.setVisible(!isExpense);
                _editTargetGoal.setManaged(!isExpense);
                if (isExpense && _editTargetGroup.getSelectedToggle() == _editTargetGoal) {
                    _editTargetWallet.setSelected(true);
                }
            }
        });

        _editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
        _editCategoryCombo.getItems().addAll("Food", "Transport", "Salary", "Utilities", "Entertainment", "Freelance");

        _editTargetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            _editTargetCombo.getItems().clear();
            if (newToggle == _editTargetWallet) {
                _editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            } else {
                _editTargetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            }
        });

        _loadTransactions();
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
    }

    @FXML
    private void _handleAddTransaction() {
        // TODO: Call transactionService.addTransaction(tx)
        System.out.println("Add transaction: " + _amountField.getText());
        _toggleForm();
        _loadTransactions();
    }

    private void _loadTransactions() {
        _transactionRows.getChildren().clear();

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
            editBtn.setOnAction(e -> _handleEditTransaction(rowData));

            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("btn-action-delete");
            deleteBtn.setOnAction(e -> _handleDeleteTransaction(rowData));

            HBox actions = new HBox(6, editBtn, deleteBtn);
            actions.setAlignment(Pos.CENTER_LEFT);
            actions.setPrefWidth(120);
            actions.setPadding(new Insets(0, 8, 0, 8));

            row.getChildren().addAll(desc, target, cat, type, amount, date, actions);
            _transactionRows.getChildren().add(row);
        }
    }

    @FXML
    private void _closeEditForm() {
        _editFormPanel.setVisible(false);
        _editFormPanel.setManaged(false);
        _currentEditRow = null;
    }

    @FXML
    private void _handleSaveEdit() {
        if (_currentEditRow == null) {
            return;
        }
        // TODO: Call transactionService.updateTransaction(id, updatedData)
        System.out.println("Saved edit for: " + _currentEditRow[0]
                + " -> new amount: " + _editAmountField.getText());
        _closeEditForm();
        _loadTransactions();
    }

    @FXML
    private void _handleEditTransaction(String[] rowData) {
        _currentEditRow = rowData;

        // Close the add-form if it is open
        _formPanel.setVisible(false);
        _formPanel.setManaged(false);

        // Pre-populate amount (strip leading sign and " EGP" if present)
        String rawAmount = rowData[4].replace("+", "").replace("-", "").trim();
        _editAmountField.setText(rawAmount);

        // Pre-select type toggle
        String type = rowData[3];
        if (type.equals("Deposit")) {
            _editDepositToggle.setSelected(true);
            _editTargetGoal.setVisible(true);
            _editTargetGoal.setManaged(true);
        } else {
            _editExpenseToggle.setSelected(true);
            _editTargetGoal.setVisible(false);
            _editTargetGoal.setManaged(false);
        }

        // Pre-select target toggle and populate combo
        String targetRaw = rowData[1];
        boolean isGoal = targetRaw.startsWith("\u25c7");
        _editTargetCombo.getItems().clear();
        if (isGoal) {
            _editTargetGoal.setSelected(true);
            _editTargetCombo.getItems().addAll("New Laptop", "Summer Vacation");
            String goalName = targetRaw.replace("\u25c7", "").trim();
            _editTargetCombo.setValue(goalName);
        } else {
            _editTargetWallet.setSelected(true);
            _editTargetCombo.getItems().addAll("Main Bank Account", "Pocket Cash", "Business Account");
            String walletName = targetRaw.replace("\u25c8", "").trim();
            _editTargetCombo.setValue(walletName);
        }

        // Pre-select category
        _editCategoryCombo.setValue(rowData[2]);

        // Clear note (no note in dummy data)
        _editNoteArea.clear();

        // Show the edit form
        _editFormPanel.setVisible(true);
        _editFormPanel.setManaged(true);
    }

    private void _handleDeleteTransaction(String[] rowData) {
        // TODO: Call transactionService.deleteTransaction(id) then refresh
        System.out.println("Delete transaction: " + rowData[0]);
        _loadTransactions();
    }

    @Override
    public void refreshData() {
        _loadTransactions();
    }
}
