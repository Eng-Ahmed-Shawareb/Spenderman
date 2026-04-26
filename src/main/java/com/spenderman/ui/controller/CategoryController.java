package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

/**
 * Categories screen controller.
 * UML: CategoryController extends BaseController implements IObserver
 * Services: categoryService
 */
public class CategoryController extends BaseController {

    @FXML private VBox formPanel;
    @FXML private TextField nameField;
    @FXML private TextField colorField;
    @FXML private ToggleButton expenseToggle;
    @FXML private ToggleButton depositToggle;
    @FXML private GridPane categoryGrid;

    private ToggleGroup typeGroup;

    @Override
    public void initialize() {
        typeGroup = new ToggleGroup();
        expenseToggle.setToggleGroup(typeGroup);
        depositToggle.setToggleGroup(typeGroup);
        expenseToggle.setSelected(true);

        loadCategories();
    }

    @FXML
    private void toggleForm() {
        boolean show = !formPanel.isVisible();
        formPanel.setVisible(show);
        formPanel.setManaged(show);
    }

    @FXML
    private void handleAddCategory() {
        // TODO: Call categoryService.createCategory(cat)
        System.out.println("Create category: " + nameField.getText() + " color: " + colorField.getText());
        toggleForm();
        loadCategories();
    }

    private void loadCategories() {
        categoryGrid.getChildren().clear();

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

            categoryGrid.add(card, i % 3, i / 3);
            GridPane.setHgrow(card, Priority.ALWAYS);
        }
    }

    @Override
    public void refreshData() {
        loadCategories();
    }
}
