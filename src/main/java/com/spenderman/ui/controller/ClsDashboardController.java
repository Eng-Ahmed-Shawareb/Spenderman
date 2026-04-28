package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.interfaceClass.IObserver;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Dashboard screen controller.
 * UML: ClsDashboardController extends ABaseController implements IObserver
 * Services: walletService, transactionService, cycleService, goalService
 */
public class ClsDashboardController extends ABaseController implements IObserver {

    @FXML private Label _totalBalanceLabel;
    @FXML private VBox _cycleCard;
    @FXML private VBox _noCycleCard;
    @FXML private Label _cycleTitleLabel;
    @FXML private Label _cycleDateLabel;
    @FXML private Label _cycleStatusBadge;
    @FXML private Label _cycleBudgetLabel;
    @FXML private Label _cycleSpentLabel;
    @FXML private Label _cycleRemainingLabel;
    @FXML private ProgressBar _cycleProgressBar;
    @FXML private Label _cyclePercentLabel;

    @FXML private HBox _expenseChartContent;
    @FXML private HBox _depositChartContent;
    @FXML private HBox _walletChartContent;

    // Dummy data matching the React prototype
    private static final double[][] _EXPENSE_SEGS = {
            {35, 0xF5, 0xA6, 0x23}, // Food - Amber
            {20, 0x88, 0x75, 0xF5}, // Transport - Purple
            {15, 0xF4, 0x72, 0xB6}, // Utilities - Pink
            {18, 0x4B, 0x9E, 0xF8}, // Entertainment - Blue
            {12, 0x4A, 0x52, 0x68}, // Other - Muted
    };
    private static final String[] _EXPENSE_LABELS = {"Food", "Transport", "Utilities", "Entertainment", "Other"};

    private static final double[][] _DEPOSIT_SEGS = {
            {72, 0x22, 0xC9, 0x7A}, // Salary - Green
            {18, 0x4B, 0x9E, 0xF8}, // Freelance - Blue
            {10, 0x4A, 0x52, 0x68}, // Other - Muted
    };
    private static final String[] _DEPOSIT_LABELS = {"Salary", "Freelance", "Other"};

    private static final double[][] _WALLET_SEGS = {
            {78, 0x88, 0x75, 0xF5}, // Business - Purple
            {21, 0x22, 0xC9, 0x7A}, // Main Bank - Green
            {1,  0x4B, 0x9E, 0xF8}, // Pocket Cash - Blue
    };
    private static final String[] _WALLET_LABELS = {"Business", "Main Bank", "Pocket Cash"};

    @Override
    public void initialize() {
        _loadTotalBalance();
        _loadCycleSummary();
        _loadExpensePieChart();
        _loadDepositPieChart();
        _loadWalletPieChart();
    }

    private void _loadTotalBalance() {
        // TODO: _totalBalanceLabel.setText("EGP " + walletService.getTotalBalance(userId));
        _totalBalanceLabel.setText("EGP 58,450.00");
    }

    private void _loadCycleSummary() {
        // TODO: use cycleService.getActiveCycle(userId)
        boolean hasCycle = true;
        _cycleCard.setVisible(hasCycle);
        _cycleCard.setManaged(hasCycle);
        _noCycleCard.setVisible(!hasCycle);
        _noCycleCard.setManaged(!hasCycle);
    }

    private void _loadExpensePieChart() {
        _buildDonutChart(_expenseChartContent, _EXPENSE_SEGS, _EXPENSE_LABELS);
    }

    private void _loadDepositPieChart() {
        _buildDonutChart(_depositChartContent, _DEPOSIT_SEGS, _DEPOSIT_LABELS);
    }

    private void _loadWalletPieChart() {
        _buildDonutChart(_walletChartContent, _WALLET_SEGS, _WALLET_LABELS);
    }

    /**
     * Build a donut chart using Canvas + legend labels.
     * Replicates the React Donut + Legend components.
     */
    private void _buildDonutChart(HBox container, double[][] segs, String[] labels) {
        container.getChildren().clear();

        int size = 90;
        int strokeWidth = 14;
        Canvas canvas = new Canvas(size, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double cx = size / 2.0;
        double cy = size / 2.0;
        double r = cx - strokeWidth / 2.0 - 2;

        // Draw arcs
        double startAngle = 90; // Start from top
        for (double[] seg : segs) {
            double pct = seg[0];
            double sweep = pct / 100.0 * 360.0;
            Color color = Color.rgb((int) seg[1], (int) seg[2], (int) seg[3]);

            gc.setStroke(color);
            gc.setLineWidth(strokeWidth);
            gc.strokeArc(cx - r, cy - r, r * 2, r * 2, startAngle, -sweep,
                    javafx.scene.shape.ArcType.OPEN);
            startAngle -= sweep;
        }

        // Draw center circle (hole)
        double innerR = r - strokeWidth / 2.0 - 1;
        gc.setFill(Color.web("#080B12"));
        gc.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);

        // Draw center text (top segment percentage)
        int topIdx = 0;
        double topPct = 0;
        for (int i = 0; i < segs.length; i++) {
            if (segs[i][0] > topPct) { topPct = segs[i][0]; topIdx = i; }
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.web("#DCE1EE"));
        gc.setFont(Font.font("System", FontWeight.BOLD, 10));
        gc.fillText((int) topPct + "%", cx, cy - 1);
        gc.setFill(Color.web("#4A5268"));
        gc.setFont(Font.font("System", 8));
        gc.fillText(labels[topIdx], cx, cy + 10);

        container.getChildren().add(canvas);

        // Legend
        VBox legend = new VBox(5);
        legend.getStyleClass().add("bg-transparent");
        for (int i = 0; i < segs.length; i++) {
            HBox row = new HBox(5);
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            row.getStyleClass().add("bg-transparent");

            // Dot
            javafx.scene.layout.Region dot = new javafx.scene.layout.Region();
            dot.getStyleClass().add("legend-dot");
            Color c = Color.rgb((int) segs[i][1], (int) segs[i][2], (int) segs[i][3]);
            dot.setStyle("-fx-background-color: " + _toHex(c) + ";");

            Label name = new Label(labels[i]);
            name.getStyleClass().add("legend-label");
            HBox.setHgrow(name, javafx.scene.layout.Priority.ALWAYS);

            Label value = new Label((int) segs[i][0] + "%");
            value.getStyleClass().add("legend-value");

            row.getChildren().addAll(dot, name, value);
            legend.getChildren().add(row);
        }
        container.getChildren().add(legend);
    }

    private String _toHex(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    @Override
    public void refreshData() {
        _loadTotalBalance();
        _loadCycleSummary();
        _loadExpensePieChart();
        _loadDepositPieChart();
        _loadWalletPieChart();
    }

    @Override
    public void update(EnEvenType evenType, Object data) {

    }
}
