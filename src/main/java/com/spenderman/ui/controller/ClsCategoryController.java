package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.StatusEnums.EnTransactionType;
import com.spenderman.service.ClsCategoryService;
import com.spenderman.service.ClsTransactionService;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Categories screen controller.
 * UML: ClsCategoryController extends ABaseController implements IObserver
 * Services: categoryService
 */
public class ClsCategoryController extends ABaseController implements IObserver {

    @FXML private VBox _formPanel;
    @FXML private TextField _nameField;
    @FXML private ColorPicker _colorPicker;
    @FXML private ToggleButton _expenseToggle;
    @FXML private ToggleButton _depositToggle;
    @FXML private GridPane _categoryGrid;
    @FXML private Label _errorLabel;

    private ToggleGroup _typeGroup;
    private ClsCategoryService _categoryService;
    private ClsTransactionService _transactionService;

    public ClsCategoryController() {
        _categoryService = new ClsCategoryService();
        _transactionService = new ClsTransactionService();
    }

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
        
        _typeGroup = new ToggleGroup();
        _expenseToggle.setToggleGroup(_typeGroup);
        _depositToggle.setToggleGroup(_typeGroup);
        _expenseToggle.setSelected(true);

        // Default color: amber
        _colorPicker.setValue(Color.web("#F59E0B"));

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
    private void _handleAddCategory() {
        if (_nameField.getText().trim().isEmpty()) {
            _errorLabel.setText("⚠ Please enter a category name.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }

        // Convert ColorPicker value to hex string
        Color c = _colorPicker.getValue();
        String hex = String.format("#%02X%02X%02X",
                (int) (c.getRed()   * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue()  * 255));

        EnTransactionType type = _expenseToggle.isSelected() ? EnTransactionType.EXPENSE : EnTransactionType.DEPOSIT;

        ClsCategory cat = new ClsCategory(0, $currentUser.getUserID(), _nameField.getText().trim(), hex, type);
        _categoryService.createCategory(cat);
        
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.CATEGORY_ADDED, cat);

        _nameField.clear();
        _toggleForm();
        _loadCategories();
    }

    private void _loadCategories() {
        _categoryGrid.getChildren().clear();
        if ($currentUser == null) return;

        List<ClsCategory> categories = _categoryService.getByUser($currentUser.getUserID());
        List<ClsTransaction> allTxs = _transactionService.getByUser($currentUser.getUserID());

        for (int i = 0; i < categories.size(); i++) {
            ClsCategory c = categories.get(i);
            long txCount = allTxs.stream().filter(tx -> tx.get_categoryID() == c.get_categoryID()).count();

            VBox card = new VBox();
            card.getStyleClass().add("card");

            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            // Color square
            Region colorDot = new Region();
            colorDot.getStyleClass().add("category-dot");
            colorDot.setStyle("-fx-background-color: " + c.get_hexColor() + ";");

            // Info
            VBox info = new VBox(2);
            info.getStyleClass().add("bg-transparent");
            Label catName = new Label(c.get_name());
            catName.getStyleClass().addAll("text-primary");
            catName.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

            String typeName = c.get_type() == EnTransactionType.DEPOSIT ? "Deposit" : "Expense";
            Label catType = new Label(typeName + " · " + txCount + " txns");
            catType.getStyleClass().addAll(typeName.equals("Deposit") ? "text-green" : "text-red");
            catType.setStyle("-fx-font-size: 9px;");

            info.getChildren().addAll(catName, catType);
            HBox.setHgrow(info, Priority.ALWAYS);

            // Delete button
            Button delBtn = new Button("×");
            delBtn.getStyleClass().add("btn-close");
            delBtn.setOnAction(e -> _handleDeleteCategory(c));

            row.getChildren().addAll(colorDot, info, delBtn);
            card.getChildren().add(row);

            _categoryGrid.add(card, i % 3, i / 3);
            GridPane.setHgrow(card, Priority.ALWAYS);
        }
    }

    private void _handleDeleteCategory(ClsCategory c) {
        _categoryService.delete(c.get_categoryID());
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.CATEGORY_DELETED, c);
        _loadCategories();
    }

    @Override
    public void refreshData() {
        if ($currentUser == null) return;
        _loadCategories();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.CATEGORY_ADDED || evenType == EnEvenType.CATEGORY_DELETED ||
            evenType == EnEvenType.TRANSACTION_ADDED || evenType == EnEvenType.TRANSACTION_UPDATED || evenType == EnEvenType.TRANSACTION_DELETED) {
            refreshData();
        }
    }
}
