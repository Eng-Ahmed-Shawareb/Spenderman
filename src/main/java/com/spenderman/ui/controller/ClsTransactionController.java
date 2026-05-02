package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.model.*;
import com.spenderman.model.StatusEnums.EnTransactionType;
import com.spenderman.service.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
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
public class ClsTransactionController extends ABaseController implements IObserver {

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
    @FXML private Label _addErrorLabel;

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
    @FXML private Label _editErrorLabel;

    @FXML private VBox _transactionRows;

    private ToggleGroup _typeGroup;
    private ToggleGroup _targetGroup;
    private ToggleGroup _editTypeGroup;
    private ToggleGroup _editTargetGroup;

    private ClsTransaction _currentEditTx;

    private ClsTransactionService _transactionService;
    private ClsWalletService _walletService;
    private ClsCategoryService _categoryService;
    private ClsSavingGoalService _savingGoalService;

    private List<ClsWallet> _wallets;
    private List<ClsSavingGoal> _goals;
    private List<ClsCategory> _categories;

    public ClsTransactionController() {
        _transactionService = new ClsTransactionService();
        _walletService = new ClsWalletService();
        _categoryService = new ClsCategoryService();
        _savingGoalService = new ClsSavingGoalService();
    }

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
        
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
                _updateCategoryCombo();
            }
        });

        _expenseToggle.setSelected(true);
        _targetGoal.setVisible(false);
        _targetGoal.setManaged(false);

        // When target type changes, update combo options
        _targetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            _targetCombo.getItems().clear();
            if (newToggle == _targetWallet && _wallets != null) {
                _targetCombo.getItems().addAll(_wallets.stream().map(ClsWallet::get_name).collect(Collectors.toList()));
            } else if (_goals != null) {
                _targetCombo.getItems().addAll(_goals.stream().map(ClsSavingGoal::get_name).collect(Collectors.toList()));
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
                _updateEditCategoryCombo();
            }
        });

        _editTargetGroup.selectedToggleProperty().addListener((obs, old, newToggle) -> {
            _editTargetCombo.getItems().clear();
            if (newToggle == _editTargetWallet && _wallets != null) {
                _editTargetCombo.getItems().addAll(_wallets.stream().map(ClsWallet::get_name).collect(Collectors.toList()));
            } else if (_goals != null) {
                _editTargetCombo.getItems().addAll(_goals.stream().map(ClsSavingGoal::get_name).collect(Collectors.toList()));
            }
        });

        if ($currentUser != null) {
            refreshData();
        }
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
        _addErrorLabel.setVisible(false);
        _addErrorLabel.setManaged(false);
    }

    @FXML
    private void _handleAddTransaction() {
        if (_amountField.getText().isEmpty() || _categoryCombo.getValue() == null || _targetCombo.getValue() == null) {
            _addErrorLabel.setText("⚠ Please fill in all required fields.");
            _addErrorLabel.setVisible(true);
            _addErrorLabel.setManaged(true);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(_amountField.getText());
        } catch (NumberFormatException e) {
            _addErrorLabel.setText("⚠ Please enter a valid numeric amount.");
            _addErrorLabel.setVisible(true);
            _addErrorLabel.setManaged(true);
            return;
        }

        EnTransactionType type = _expenseToggle.isSelected() ? EnTransactionType.EXPENSE : EnTransactionType.DEPOSIT;
        
        int walletId = 0, goalId = 0;
        if (_targetWallet.isSelected()) {
            walletId = _wallets.stream().filter(w -> w.get_name().equals(_targetCombo.getValue())).findFirst().get().get_walletID();
        } else {
            goalId = _goals.stream().filter(g -> g.get_name().equals(_targetCombo.getValue())).findFirst().get().get_goalID();
        }
        
        int catId = _categories.stream().filter(c -> c.get_name().equals(_categoryCombo.getValue())).findFirst().get().get_categoryID();
        
        ClsTransaction tx = new ClsTransaction(0, walletId, goalId, catId, amount, LocalDateTime.now(), type, _noteArea.getText());
        _transactionService.addTransaction(tx);
        
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.TRANSACTION_ADDED, tx);
        
        _amountField.clear();
        _noteArea.clear();
        _toggleForm();
        _loadTransactions();
    }

    private void _loadTransactions() {
        _transactionRows.getChildren().clear();
        if ($currentUser == null) return;
        
        List<ClsTransaction> txs = _transactionService.getByUser($currentUser.getUserID());
        
        for (ClsTransaction tx : txs) {
            HBox row = new HBox();
            row.getStyleClass().add("table-row");
            row.setAlignment(Pos.CENTER_LEFT);

            String noteText = (tx.get_note() != null && !tx.get_note().isEmpty()) ? tx.get_note() : "Transaction";
            Label desc = new Label(noteText);
            desc.getStyleClass().add("table-cell");
            desc.setPrefWidth(170);

            String targetName = "Unknown";
            String targetPrefix = "";
            if (tx.get_walletID() > 0 && _wallets != null) {
                targetPrefix = "◈ ";
                ClsWallet w = _wallets.stream().filter(x -> x.get_walletID() == tx.get_walletID()).findFirst().orElse(null);
                if (w != null) targetName = w.get_name();
            } else if (tx.get_savingGoalID() > 0 && _goals != null) {
                targetPrefix = "◇ ";
                ClsSavingGoal g = _goals.stream().filter(x -> x.get_goalID() == tx.get_savingGoalID()).findFirst().orElse(null);
                if (g != null) targetName = g.get_name();
            }

            Label target = new Label(targetPrefix + targetName);
            target.getStyleClass().addAll("table-cell", targetPrefix.equals("◇ ") ? "text-pink" : "text-sub");
            target.setPrefWidth(130);

            String catName = "Unknown";
            if (_categories != null) {
                ClsCategory c = _categories.stream().filter(x -> x.get_categoryID() == tx.get_categoryID()).findFirst().orElse(null);
                if (c != null) catName = c.get_name();
            }
            Label cat = new Label(catName);
            cat.getStyleClass().addAll("table-cell", "text-sub");
            cat.setPrefWidth(100);

            boolean isDeposit = tx.get_type() == EnTransactionType.DEPOSIT;
            Label type = new Label(isDeposit ? "Deposit" : "Expense");
            type.getStyleClass().addAll(isDeposit ? "badge-green" : "badge-red");
            type.setPrefWidth(85);

            String sign = isDeposit ? "+" : "-";
            Label amount = new Label(sign + tx.get_amount() + " EGP");
            amount.getStyleClass().addAll("table-cell", "mono", isDeposit ? "text-green" : "text-red");
            amount.setPrefWidth(110);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Label date = new Label(tx.get_localDateTime() != null ? tx.get_localDateTime().format(formatter) : "");
            date.getStyleClass().addAll("table-cell", "text-muted");
            date.setPrefWidth(90);

            Button editBtn = new Button("Edit");
            editBtn.getStyleClass().add("btn-action-edit");
            editBtn.setOnAction(e -> _handleEditTransaction(tx));

            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("btn-action-delete");
            deleteBtn.setOnAction(e -> _handleDeleteTransaction(tx));

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
        _currentEditTx = null;
        _editErrorLabel.setVisible(false);
        _editErrorLabel.setManaged(false);
    }

    @FXML
    private void _handleSaveEdit() {
        if (_currentEditTx == null) return;
        
        if (_editAmountField.getText().isEmpty() || _editCategoryCombo.getValue() == null || _editTargetCombo.getValue() == null) {
            _editErrorLabel.setText("⚠ Please fill in all required fields.");
            _editErrorLabel.setVisible(true);
            _editErrorLabel.setManaged(true);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(_editAmountField.getText());
        } catch (NumberFormatException e) {
            _editErrorLabel.setText("⚠ Please enter a valid numeric amount.");
            _editErrorLabel.setVisible(true);
            _editErrorLabel.setManaged(true);
            return;
        }

        EnTransactionType type = _editExpenseToggle.isSelected() ? EnTransactionType.EXPENSE : EnTransactionType.DEPOSIT;
        
        int walletId = 0, goalId = 0;
        if (_editTargetWallet.isSelected()) {
            walletId = _wallets.stream().filter(w -> w.get_name().equals(_editTargetCombo.getValue())).findFirst().get().get_walletID();
        } else {
            goalId = _goals.stream().filter(g -> g.get_name().equals(_editTargetCombo.getValue())).findFirst().get().get_goalID();
        }
        
        int catId = _categories.stream().filter(c -> c.get_name().equals(_editCategoryCombo.getValue())).findFirst().get().get_categoryID();
        
        _currentEditTx.set_amount(amount);
        _currentEditTx.set_type(type);
        _currentEditTx.set_walletID(walletId);
        _currentEditTx.set_savingGoalID(goalId);
        _currentEditTx.set_categoryID(catId);
        _currentEditTx.set_note(_editNoteArea.getText());
        
        _transactionService.updateTransaction(_currentEditTx);
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.TRANSACTION_UPDATED, _currentEditTx);
        
        _closeEditForm();
        _loadTransactions();
    }

    @FXML
    private void _handleEditTransaction(ClsTransaction tx) {
        _currentEditTx = tx;

        _formPanel.setVisible(false);
        _formPanel.setManaged(false);

        _editAmountField.setText(String.valueOf(tx.get_amount()));

        if (tx.get_type() == EnTransactionType.DEPOSIT) {
            _editDepositToggle.setSelected(true);
            _editTargetGoal.setVisible(true);
            _editTargetGoal.setManaged(true);
        } else {
            _editExpenseToggle.setSelected(true);
            _editTargetGoal.setVisible(false);
            _editTargetGoal.setManaged(false);
        }
        
        _updateEditCategoryCombo();

        if (tx.get_walletID() > 0 && _wallets != null) {
            _editTargetWallet.setSelected(true);
            ClsWallet w = _wallets.stream().filter(x -> x.get_walletID() == tx.get_walletID()).findFirst().orElse(null);
            if (w != null) _editTargetCombo.setValue(w.get_name());
        } else if (tx.get_savingGoalID() > 0 && _goals != null) {
            _editTargetGoal.setSelected(true);
            ClsSavingGoal g = _goals.stream().filter(x -> x.get_goalID() == tx.get_savingGoalID()).findFirst().orElse(null);
            if (g != null) _editTargetCombo.setValue(g.get_name());
        }

        if (_categories != null) {
            ClsCategory c = _categories.stream().filter(x -> x.get_categoryID() == tx.get_categoryID()).findFirst().orElse(null);
            if (c != null) _editCategoryCombo.setValue(c.get_name());
        }

        _editNoteArea.setText(tx.get_note());

        _editFormPanel.setVisible(true);
        _editFormPanel.setManaged(true);
    }

    private void _handleDeleteTransaction(ClsTransaction tx) {
        _transactionService.deleteTransaction(tx.get_transactionID());
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.TRANSACTION_DELETED, tx);
        _loadTransactions();
    }

    @Override
    public void refreshData() {
        if ($currentUser == null) return;
        _wallets = _walletService.getByUser($currentUser.getUserID());
        _goals = _savingGoalService.getByUser($currentUser.getUserID());
        try {
            _categories = _categoryService.getByUser($currentUser.getUserID());
        } catch (Exception e) {
            _categories = new java.util.ArrayList<>();
        }

        // populate categories
        _updateCategoryCombo();
        _updateEditCategoryCombo();

        // re-trigger combo populations for targets
        ToggleButton selected = (ToggleButton) _targetGroup.getSelectedToggle();
        _targetCombo.getItems().clear();
        if (selected == _targetWallet && _wallets != null) {
            _targetCombo.getItems().addAll(_wallets.stream().map(ClsWallet::get_name).collect(Collectors.toList()));
        } else if (_goals != null) {
            _targetCombo.getItems().addAll(_goals.stream().map(ClsSavingGoal::get_name).collect(Collectors.toList()));
        }

        ToggleButton editSelected = (ToggleButton) _editTargetGroup.getSelectedToggle();
        _editTargetCombo.getItems().clear();
        if (editSelected == _editTargetWallet && _wallets != null) {
            _editTargetCombo.getItems().addAll(_wallets.stream().map(ClsWallet::get_name).collect(Collectors.toList()));
        } else if (_goals != null) {
            _editTargetCombo.getItems().addAll(_goals.stream().map(ClsSavingGoal::get_name).collect(Collectors.toList()));
        }

        _loadTransactions();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.TRANSACTION_ADDED || evenType == EnEvenType.TRANSACTION_DELETED || evenType == EnEvenType.TRANSACTION_UPDATED) {
            refreshData();
        }
    }

    private void _updateCategoryCombo() {
        if (_categories == null) return;
        EnTransactionType type = _expenseToggle.isSelected() ? EnTransactionType.EXPENSE : EnTransactionType.DEPOSIT;
        String currentSelection = _categoryCombo.getValue();
        _categoryCombo.getItems().clear();
        _categoryCombo.getItems().addAll(_categories.stream()
                .filter(c -> c.get_type() == type)
                .map(ClsCategory::get_name)
                .collect(Collectors.toList()));
        if (currentSelection != null && _categoryCombo.getItems().contains(currentSelection)) {
            _categoryCombo.setValue(currentSelection);
        }
    }

    private void _updateEditCategoryCombo() {
        if (_categories == null) return;
        EnTransactionType type = _editExpenseToggle.isSelected() ? EnTransactionType.EXPENSE : EnTransactionType.DEPOSIT;
        String currentSelection = _editCategoryCombo.getValue();
        _editCategoryCombo.getItems().clear();
        _editCategoryCombo.getItems().addAll(_categories.stream()
                .filter(c -> c.get_type() == type)
                .map(ClsCategory::get_name)
                .collect(Collectors.toList()));
        if (currentSelection != null && _editCategoryCombo.getItems().contains(currentSelection)) {
            _editCategoryCombo.setValue(currentSelection);
        }
    }
}
