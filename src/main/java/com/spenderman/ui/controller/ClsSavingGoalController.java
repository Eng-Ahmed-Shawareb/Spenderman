package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsSavingGoal;
import com.spenderman.model.StatusEnums.EnGoalState;
import com.spenderman.service.ClsSavingGoalService;
import java.time.LocalDate;
import java.util.List;
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

    @FXML private VBox _formPanel;
    @FXML private TextField _nameField;
    @FXML private TextField _targetField;
    @FXML private DatePicker _targetDatePicker;
    @FXML private VBox _goalList;
    @FXML private Label _errorLabel;

    private ClsSavingGoalService goalService;
    
    private final String[] colors = {"#F472B6", "#22C97A", "#F5A623", "#8875F5", "#4B9EF8", "#F59E0B"};

    public ClsSavingGoalController() {
        goalService = new ClsSavingGoalService();
    }

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
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
    private void _handleAddGoal() {
        if (_nameField.getText().trim().isEmpty() || _targetField.getText().trim().isEmpty()) {
            _errorLabel.setText("⚠ Please provide both a goal name and a target amount.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }

        double targetAmount;
        try {
            targetAmount = Double.parseDouble(_targetField.getText().trim());
            if (targetAmount <= 0) {
                _errorLabel.setText("⚠ Target amount must be positive.");
                _errorLabel.setVisible(true);
                _errorLabel.setManaged(true);
                return;
            }
        } catch (NumberFormatException e) {
            _errorLabel.setText("⚠ Target amount must be a valid number.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }

        LocalDate targetDate = _targetDatePicker.getValue();

        ClsSavingGoal newGoal = new ClsSavingGoal(0, $currentUser.getUserID(), targetAmount, _nameField.getText().trim(), 0, targetDate, EnGoalState.ACTIVE);

        goalService.createGoal(newGoal);
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.GOAL_ADDED, newGoal);

        _nameField.clear();
        _targetField.clear();
        _targetDatePicker.setValue(null);
        _toggleForm();
        _loadGoals();
    }

    private void _loadGoals() {
        _goalList.getChildren().clear();
        if ($currentUser == null) return;

        List<ClsSavingGoal> goals = goalService.getByUser($currentUser.getUserID());

        for (int i = 0; i < goals.size(); i++) {
            ClsSavingGoal g = goals.get(i);
            
            VBox card = new VBox(8);
            card.getStyleClass().add("card");

            double cur = g.get_currentSaved();
            double tgt = g.get_targetAmount();
            double pct = tgt > 0 ? Math.min((cur / tgt) * 100, 100) : 0;
            boolean completed = g.getStatus() == EnGoalState.COMPLETED;
            String color = colors[i % colors.length];

            // Title row: dot + name + status + add button
            HBox titleRow = new HBox(8);
            titleRow.setAlignment(Pos.CENTER_LEFT);

            Circle dot = new Circle(5);
            dot.setStyle("-fx-fill: " + color + ";");

            Label goalName = new Label(g.get_name());
            goalName.getStyleClass().add("section-title");
            HBox.setHgrow(goalName, Priority.ALWAYS);

            HBox badges = new HBox(6);
            badges.setAlignment(Pos.CENTER);
            
            String statusText = completed ? "Completed" : "Active";
            Label status = new Label(statusText);
            status.getStyleClass().add(completed ? "badge-green" : "badge-amber");
            badges.getChildren().add(status);

            if (!completed) {
                Button addBtn = new Button("+ Add");
                addBtn.getStyleClass().add("btn-outline-small");
                addBtn.setOnAction(e -> _toggleAddAmountForm(card, g));
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

            if (g.get_targetDate() != null) {
                Label targetDate = new Label("Target: " + g.get_targetDate().toString());
                targetDate.getStyleClass().add("text-muted");
                targetDate.setStyle("-fx-font-size: 9px;");
                footer.getChildren().add(targetDate);
            }

            card.getChildren().addAll(titleRow, amounts, bar, footer);
            _goalList.getChildren().add(card);
        }
    }

    private void _toggleAddAmountForm(VBox card, ClsSavingGoal goal) {
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
            try {
                double amount = Double.parseDouble(addAmount.getText().trim());
                if (amount > 0) {
                    goalService.addAmount(goal.get_goalID(), amount);
                    ClsAppEventBus.getInstance().notifyObservers(EnEvenType.GOAL_UPDATED, goal);
                    _loadGoals();
                }
            } catch (NumberFormatException ex) {
                // Ignore invalid input
            }
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
        if ($currentUser == null) return;
        _loadGoals();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.GOAL_ADDED || evenType == EnEvenType.GOAL_UPDATED || evenType == EnEvenType.GOAL_DELETED) {
            refreshData();
        }
    }
}
