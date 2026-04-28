package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
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

    private ToggleGroup _typeGroup;

    @Override
    public void initialize() {
        _typeGroup = new ToggleGroup();
        _expenseToggle.setToggleGroup(_typeGroup);
        _depositToggle.setToggleGroup(_typeGroup);
        _expenseToggle.setSelected(true);

        // Default color: amber
        _colorPicker.setValue(Color.web("#F59E0B"));

        _loadCategories();
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
    }

    @FXML
    private void _handleAddCategory() {
        // Convert ColorPicker value to hex string
        Color c = _colorPicker.getValue();
        String hex = String.format("#%02X%02X%02X",
                (int) (c.getRed()   * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue()  * 255));
        // TODO: Call categoryService.createCategory(cat)
        System.out.println("Create category: " + _nameField.getText() + " color: " + hex);
        _toggleForm();
        _loadCategories();
    }

    private void _loadCategories() {
        _categoryGrid.getChildren().clear();

        // Dummy data matching React prototype
        String[][] categories = {
                {"Food", "#F59E0B", "Expense", "18"},
                {"Transport", "#8875F5", "Expense", "7"},
                {"Salary", "#22C97A", "Deposit", "2"},
                {"Utilities", "#F472B6", "Expense", "4"},
                {"Entertainment", "#4B9EF8", "Expense", "6"},
                {"Freelance", "#F59E0B", "Deposit", "3"},
        };

        for (int i = 0; i < categories.length; i++) {
            String[] c = categories[i];
            VBox card = new VBox();
            card.getStyleClass().add("card");

            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);

            // Color square
            Region colorDot = new Region();
            colorDot.getStyleClass().add("category-dot");
            colorDot.setStyle("-fx-background-color: " + c[1] + ";");

            // Info
            VBox info = new VBox(2);
            info.getStyleClass().add("bg-transparent");
            Label catName = new Label(c[0]);
            catName.getStyleClass().addAll("text-primary");
            catName.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

            Label catType = new Label(c[2] + " · " + c[3] + " txns");
            catType.getStyleClass().addAll(c[2].equals("Deposit") ? "text-green" : "text-red");
            catType.setStyle("-fx-font-size: 9px;");

            info.getChildren().addAll(catName, catType);
            HBox.setHgrow(info, Priority.ALWAYS);

            // Delete button
            Button delBtn = new Button("×");
            delBtn.getStyleClass().add("btn-close");

            row.getChildren().addAll(colorDot, info, delBtn);
            card.getChildren().add(row);

            _categoryGrid.add(card, i % 3, i / 3);
            GridPane.setHgrow(card, Priority.ALWAYS);
        }
    }

    @Override
    public void refreshData() {
        _loadCategories();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
