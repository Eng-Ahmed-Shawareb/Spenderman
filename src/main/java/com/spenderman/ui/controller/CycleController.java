package com.spenderman.ui.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Budget Cycles screen controller.
 * UML: CycleController extends BaseController implements IObserver
 * Services: cycleService
 */
public class CycleController extends BaseController {

    @FXML private VBox formPanel;
    @FXML private TextField budgetField;
    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;
    @FXML private VBox cycleList;

    @Override
    public void initialize() {
        loadCycles();
    }

    @FXML
    private void toggleForm() {
        boolean show = !formPanel.isVisible();
        formPanel.setVisible(show);
        formPanel.setManaged(show);
    }

    @FXML
    private void handleAddCycle() {
        // TODO: Call cycleService.createCycle(cycle)
        System.out.println("Create cycle: budget=" + budgetField.getText());
        toggleForm();
        loadCycles();
    }

    private void loadCycles() {
        cycleList.getChildren().clear();

        // Dummy data matching React prototype
        String[][] cycles = {
                {"April 2025", "5000", "3200", "Active", "2025-04-01", "2025-04-30"},
                {"March 2025", "4500", "4800", "Past", "2025-03-01", "2025-03-31"},
                {"February 2025", "4000", "3700", "Past", "2025-02-01", "2025-02-28"},
        };

        for (String[] cy : cycles) {
            VBox card = new VBox(10);
            card.getStyleClass().add("card");

            double budget = Double.parseDouble(cy[1]);
            double spent = Double.parseDouble(cy[2]);
            double pct = (spent / budget) * 100;
            boolean over = spent > budget;

            // Title row
            HBox titleRow = new HBox();
            titleRow.setSpacing(12);
            titleRow.setAlignment(Pos.CENTER_LEFT);

            VBox titleInfo = new VBox(2);
            titleInfo.getStyleClass().add("bg-transparent");
            Label cycleTitle = new Label(cy[0]);
            cycleTitle.getStyleClass().add("section-title");
            Label cycleDate = new Label(cy[4] + " → " + cy[5]);
            cycleDate.getStyleClass().add("text-muted");
            titleInfo.getChildren().addAll(cycleTitle, cycleDate);
            HBox.setHgrow(titleInfo, Priority.ALWAYS);

            HBox badges = new HBox(6);
            badges.setAlignment(Pos.CENTER);
            Label status = new Label(cy[3]);
            status.getStyleClass().add(cy[3].equals("Active") ? "badge-green" : "badge-muted");
            badges.getChildren().add(status);

            if (cy[3].equals("Active")) {
                Button closeBtn = new Button("Close");
                closeBtn.getStyleClass().add("btn-outline-small");
                closeBtn.setOnAction(e -> handleCloseCycle());
                badges.getChildren().add(closeBtn);
            }

            titleRow.getChildren().addAll(titleInfo, badges);

            // Stats row
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

            // Progress bar
            ProgressBar bar = new ProgressBar(Math.min(pct / 100.0, 1.0));
            bar.getStyleClass().add("progress-bar");
            bar.setMaxWidth(Double.MAX_VALUE);

            card.getChildren().addAll(titleRow, statsRow, bar);

            // Over budget warning
            if (over) {
                Label warning = new Label("⚠ Over budget by EGP " + String.format("%,.0f", spent - budget));
                warning.getStyleClass().add("text-red");
                warning.setStyle("-fx-font-size: 9px;");
                card.getChildren().add(warning);
            }

            cycleList.getChildren().add(card);
        }
    }

    private void handleCloseCycle() {
        // TODO: Call cycleService.closeCycle(cycleId)
        System.out.println("Close active cycle");
    }

    @Override
    public void refreshData() {
        loadCycles();
    }
}
