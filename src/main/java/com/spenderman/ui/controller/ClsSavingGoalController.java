package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.service.ClsSavingGoalService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;

/**
 * Saving Goals screen controller.
 * UML: ClsSavingGoalController extends ABaseController implements IObserver
 * Services: goalService
 */
public class ClsSavingGoalController extends ABaseController implements IObserver {

    @FXML
    private VBox _formPanel;
    @FXML
    private TextField _nameField;
    @FXML
    private TextField _targetField;
    @FXML
    private DatePicker _targetDatePicker;
    @FXML
    private VBox _goalList;

    private ClsSavingGoalService goalService;

    @Override
    public void initialize() {
        _loadGoals();
    }

    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
    }

    @FXML
    private void _handleAddGoal() {


        System.out.println("Create goal: " + _nameField.getText() + " target=" + _targetField.getText());
        _toggleForm();
        _loadGoals();
    }

    private void _loadGoals() {
        _goalList.getChildren().clear();

        // Dummy data matching React prototype
        String[][] goals = {
                { "New Laptop", "3200", "8000", "2025-09-01", "Active", "#F472B6" },
                { "Summer Vacation", "1500", "5000", "2025-07-01", "Active", "#22C97A" },
                { "Emergency Fund", "10000", "10000", "", "Completed", "#F5A623" },
        };

        for (String[] g : goals) {
            VBox card = new VBox(8);
            card.getStyleClass().add("card");

            double cur = Double.parseDouble(g[1]);
            double tgt = Double.parseDouble(g[2]);
            double pct = Math.min((cur / tgt) * 100, 100);
            boolean completed = g[4].equals("Completed");
            String color = g[5];

            // Title row: dot + name + status + add button
            HBox titleRow = new HBox(8);
            titleRow.setAlignment(Pos.CENTER_LEFT);

            Circle dot = new Circle(5);
            dot.setStyle("-fx-fill: " + color + ";");

            Label goalName = new Label(g[0]);
            goalName.getStyleClass().add("section-title");
            HBox.setHgrow(goalName, Priority.ALWAYS);

            HBox badges = new HBox(6);
            badges.setAlignment(Pos.CENTER);
            Label status = new Label(g[4]);
            status.getStyleClass().add(completed ? "badge-green" : "badge-amber");
            badges.getChildren().add(status);

            if (!completed) {
                Button addBtn = new Button("+ Add");
                addBtn.getStyleClass().add("btn-outline-small");
                final String goalName2 = g[0];
                addBtn.setOnAction(e -> _toggleAddAmountForm(card, goalName2));
                badges.getChildren().add(addBtn);
            }

            titleRow.getChildren().addAll(dot, goalName, badges);

            // Amounts row
            HBox amounts = new HBox(12);
            amounts.getStyleClass().add("bg-transparent");

            Label savedLabel = new Label("Saved: ");
            savedLabel.getStyleClass().add("text-sub");
            Label savedAmt = new Label("EGP " + String.format("%,.0f", cur));
            savedAmt.getStyleClass().add("mono");
            savedAmt.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");

            Label targetLabel = new Label("Target: ");
            targetLabel.getStyleClass().add("text-sub");
            Label targetAmt = new Label("EGP " + String.format("%,.0f", tgt));
            targetAmt.getStyleClass().addAll("mono", "text-primary");

            amounts.getChildren().addAll(savedLabel, savedAmt, targetLabel, targetAmt);

            // Progress bar
            ProgressBar bar = new ProgressBar(pct / 100.0);
            bar.getStyleClass().addAll("progress-bar", "progress-bar-tall");
            bar.setMaxWidth(Double.MAX_VALUE);
            bar.setStyle("-fx-accent: " + color + ";");

            // Footer
            HBox footer = new HBox(12);
            footer.getStyleClass().add("bg-transparent");
            Label progressLabel = new Label(String.format("%.0f%% complete", pct));
            progressLabel.getStyleClass().add("text-muted");
            progressLabel.setStyle("-fx-font-size: 9px;");
            HBox.setHgrow(progressLabel, Priority.ALWAYS);
            footer.getChildren().add(progressLabel);

            if (!g[3].isEmpty()) {
                Label targetDate = new Label("Target: " + g[3]);
                targetDate.getStyleClass().add("text-muted");
                targetDate.setStyle("-fx-font-size: 9px;");
                footer.getChildren().add(targetDate);
            }

            card.getChildren().addAll(titleRow, amounts, bar, footer);
            _goalList.getChildren().add(card);
        }
    }

    /**
     * Toggle a mini inline form to add money to a specific goal.
     */
    private void _toggleAddAmountForm(VBox card, String goalName) {
        // Check if form already exists
        if (card.getChildren().size() > 4 && card.getChildren().get(4) instanceof HBox addRow) {
            card.getChildren().remove(4);
            return;
        }

        HBox addRow = new HBox(8);
        addRow.getStyleClass().add("bg-surf2");
        addRow.setStyle("-fx-background-color: #161B26; -fx-background-radius: 6; -fx-padding: 10;");
        addRow.setAlignment(Pos.CENTER_LEFT);

        TextField addAmount = new TextField();
        addAmount.setPromptText("Amount to add (EGP)");
        HBox.setHgrow(addAmount, Priority.ALWAYS);

        Button addBtn = new Button("Add");
        addBtn.getStyleClass().add("btn-small");
        addBtn.setOnAction(e -> {
            // TODO: Call goalService.addAmount(goalId, amount)
            System.out.println("Add " + addAmount.getText() + " to " + goalName);
            card.getChildren().remove(addRow);
        });

        Button closeBtn = new Button("×");
        closeBtn.getStyleClass().add("btn-close");
        closeBtn.setOnAction(e -> card.getChildren().remove(addRow));

        addRow.getChildren().addAll(addAmount, addBtn, closeBtn);

        // Insert after titleRow (index 1), but before amounts
        card.getChildren().add(1, addRow);
    }

    @Override
    public void refreshData() {
        _loadGoals();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
