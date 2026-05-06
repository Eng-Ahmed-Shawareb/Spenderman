package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsCycle;
import com.spenderman.model.StatusEnums.EnCycleState;
import com.spenderman.service.ClsCycleService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

/**
 * Class representing ClsCycleController.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsCycleController extends ABaseController implements IObserver {

    @FXML
    private VBox _formPanel;

    @FXML
    private TextField _budgetField;

    @FXML
    private DatePicker _endPicker;

    @FXML
    private VBox _cycleList;

    @FXML
    private Label _errorLabel;

    @FXML
    private Button _newCycleBtn;

    private ClsCycleService cycleService;

    public ClsCycleController() {
        cycleService = new ClsCycleService();
    }

    /**
     * Method to initialize.
     */
    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
        if (_endPicker != null) {
            _endPicker.setEditable(false);
            _endPicker.getEditor().setEditable(false);
            _endPicker.getEditor().setDisable(true);
            _endPicker.getEditor().setOpacity(1.0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            _endPicker.setConverter(new javafx.util.StringConverter<LocalDate>() {

                /**
                 * Method to toString.
                 *
                 * @param date the date
                 * @return the String
                 */
                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return formatter.format(date);
                    } else {
                        return "";
                    }
                }

                /**
                 * Method to fromString.
                 *
                 * @param string the string
                 * @return the LocalDate
                 */
                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.trim().isEmpty()) {
                        try {
                            return LocalDate.parse(string, formatter);
                        } catch (Exception e) {
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
            });
        }
    }

    /**
     * Method to _toggleForm.
     */
    @FXML
    private void _toggleForm() {
        boolean show = !_formPanel.isVisible();
        _formPanel.setVisible(show);
        _formPanel.setManaged(show);
        _errorLabel.setVisible(false);
        _errorLabel.setManaged(false);
    }

    /**
     * Method to _handleAddCycle.
     */
    @FXML
    private void _handleAddCycle() {
        if (_budgetField.getText().trim().isEmpty() || _endPicker.getValue() == null) {
            _errorLabel.setText("⚠ Please provide a budget and end date.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }
        double budget;
        try {
            budget = Double.parseDouble(_budgetField.getText().trim());
            if (budget <= 0) {
                _errorLabel.setText("⚠ Budget amount must be positive.");
                _errorLabel.setVisible(true);
                _errorLabel.setManaged(true);
                return;
            }
        } catch (NumberFormatException e) {
            _errorLabel.setText("⚠ Budget amount must be a valid number.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = _endPicker.getValue().atTime(23, 59, 59);
        if (end.isBefore(start)) {
            _errorLabel.setText("⚠ End date cannot be before start date.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }
        ClsCycle newCycle = new ClsCycle($currentUser.getUserID(), 0, budget, start, end, EnCycleState.ACTIVE);
        boolean created = cycleService.createCycle(newCycle);
        if (!created) {
            _errorLabel.setText("⚠ Could not create cycle. An active cycle might already exist.");
            _errorLabel.setVisible(true);
            _errorLabel.setManaged(true);
            return;
        }
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.CYCLE_ADDED, newCycle);
        _budgetField.clear();
        _endPicker.setValue(null);
        _toggleForm();
        _loadCycles();
    }

    /**
     * Method to _loadCycles.
     */
    private void _loadCycles() {
        _cycleList.getChildren().clear();
        if ($currentUser == null)
            return;
        List<ClsCycle> cycles = cycleService.getByUser($currentUser.getUserID());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM yyyy");
        boolean hasActiveCycle = cycles.stream().anyMatch(c -> c.get_state() == EnCycleState.ACTIVE);
        if (_newCycleBtn != null) {
            _newCycleBtn.setVisible(!hasActiveCycle);
            _newCycleBtn.setManaged(!hasActiveCycle);
        }
        java.util.Collections.reverse(cycles);
        for (ClsCycle cy : cycles) {
            VBox card = new VBox(10);
            card.getStyleClass().add("card");
            double budget = cy.get_budgetAmount();
            double spent = cycleService.getTotalSpent(cy.get_cycleID());
            double pct = budget > 0 ? (spent / budget) * 100 : 0;
            boolean over = spent > budget;
            boolean active = cy.get_state() == EnCycleState.ACTIVE;
            String statusStr = active ? "Active" : "Past";
            HBox titleRow = new HBox();
            titleRow.setSpacing(12);
            titleRow.setAlignment(Pos.CENTER_LEFT);
            VBox titleInfo = new VBox(2);
            titleInfo.getStyleClass().add("bg-transparent");
            String title = cy.get_startDate().format(fmt);
            Label cycleTitle = new Label(title);
            cycleTitle.getStyleClass().add("section-title");
            Label cycleDate = new Label(cy.get_startDate().toLocalDate() + " → " + cy.get_endDate().toLocalDate());
            cycleDate.getStyleClass().add("text-muted");
            titleInfo.getChildren().addAll(cycleTitle, cycleDate);
            HBox.setHgrow(titleInfo, Priority.ALWAYS);
            HBox badges = new HBox(6);
            badges.setAlignment(Pos.CENTER);
            Label status = new Label(statusStr);
            status.getStyleClass().add(active ? "badge-green" : "badge-muted");
            badges.getChildren().add(status);
            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("btn-outline-small");
            deleteBtn.setOnAction(e -> _handleDeleteCycle(cy));
            badges.getChildren().add(deleteBtn);
            titleRow.getChildren().addAll(titleInfo, badges);
            HBox statsRow = new HBox(12);
            statsRow.getStyleClass().add("bg-transparent");
            Label spentLabel = new Label("Spent: ");
            spentLabel.getStyleClass().add("text-sub");
            Label spentAmt = new Label("EGP " + String.format("%,.0f", spent));
            spentAmt.getStyleClass().addAll("mono", over ? "text-red" : "text-primary");
            Label budgetLabel = new Label("Budget: ");
            budgetLabel.getStyleClass().add("text-sub");
            Label budgetAmt = new Label("EGP " + String.format("%,.0f", budget));
            budgetAmt.getStyleClass().addAll("mono", "text-primary");
            statsRow.getChildren().addAll(spentLabel, spentAmt, budgetLabel, budgetAmt);
            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.getStyleClass().add("progress-bar");
            if (over) {
                bar.setStyle("-fx-accent: #EF4444;");
            }
            bar.setMaxWidth(Double.MAX_VALUE);
            card.getChildren().addAll(titleRow, statsRow, bar);
            if (over) {
                Label warning = new Label("⚠ Over budget by EGP " + String.format("%,.0f", spent - budget));
                warning.getStyleClass().add("text-red");
                warning.setStyle("-fx-font-size: 9px;");
                card.getChildren().add(warning);
            }
            _cycleList.getChildren().add(card);
        }
    }

    /**
     * Method to _handleDeleteCycle.
     *
     * @param cycle the cycle
     */
    private void _handleDeleteCycle(ClsCycle cycle) {
        cycleService.deleteCycle(cycle.get_cycleID());
        ClsAppEventBus.getInstance().notifyObservers(EnEvenType.CYCLE_DELETED, cycle);
        _loadCycles();
    }

    /**
     * Method to refreshData.
     */
    @Override
    public void refreshData() {
        if ($currentUser == null)
            return;
        _loadCycles();
    }

    /**
     * Method to update.
     *
     * @param evenType the evenType
     * @param data the data
     */
    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.CYCLE_ADDED || evenType == EnEvenType.CYCLE_UPDATED || evenType == EnEvenType.CYCLE_DELETED || evenType == EnEvenType.TRANSACTION_ADDED || evenType == EnEvenType.TRANSACTION_UPDATED || evenType == EnEvenType.TRANSACTION_DELETED) {
            refreshData();
        }
    }
}
