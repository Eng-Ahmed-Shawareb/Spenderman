package com.spenderman.ui.controller;

import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;
import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.model.ClsCategory;
import com.spenderman.model.ClsCycle;
import com.spenderman.model.ClsTransaction;
import com.spenderman.model.ClsWallet;
import com.spenderman.model.StatusEnums.EnTransactionType;
import com.spenderman.service.ClsCategoryService;
import com.spenderman.service.ClsCycleService;
import com.spenderman.service.ClsSavingGoalService;
import com.spenderman.service.ClsTransactionService;
import com.spenderman.service.ClsWalletService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Dashboard screen controller.
 * UML: ClsDashboardController extends ABaseController implements IObserver
 * Services: walletService, transactionService, cycleService, goalService,
 * categoryService
 */
public class ClsDashboardController extends ABaseController implements IObserver {

    // ── Services ──────────────────────────────────────────────────────────
    private final ClsWalletService _walletService = new ClsWalletService();
    private final ClsTransactionService _transactionService = new ClsTransactionService();
    private final ClsCycleService _cycleService = new ClsCycleService();
    private final ClsSavingGoalService _savingGoalService = new ClsSavingGoalService();
    private final ClsCategoryService _categoryService = new ClsCategoryService();

    // ── FXML fields ───────────────────────────────────────────────────────
    @FXML
    private Label _totalBalanceLabel;
    @FXML
    private HBox _walletBreakdownRow;

    @FXML
    private VBox _cycleCard;
    @FXML
    private VBox _noCycleCard;
    @FXML
    private Label _cycleTitleLabel;
    @FXML
    private Label _cycleDateLabel;
    @FXML
    private Label _cycleStatusBadge;
    @FXML
    private Label _cycleBudgetLabel;
    @FXML
    private Label _cycleSpentLabel;
    @FXML
    private Label _cycleRemainingLabel;
    @FXML
    private ProgressBar _cycleProgressBar;
    @FXML
    private Label _cyclePercentLabel;

    @FXML
    private HBox _expenseChartContent;
    @FXML
    private HBox _depositChartContent;
    @FXML
    private HBox _walletChartContent;

    // Colour palette rotated for categories without a stored hex colour
    private static final String[] PALETTE = {
            "#F5A623", "#8875F5", "#F472B6", "#4B9EF8",
            "#22C97A", "#EF4444", "#F59E0B", "#6366F1"
    };

    @Override
    public void initialize() {
        ClsAppEventBus.getInstance().addObserver(this);
    }

    // ── Data loaders ──────────────────────────────────────────────────────

    private void _loadTotalBalance() {
        double total = _walletService.getTotalBalance($currentUser.getUserID());
        _totalBalanceLabel.setText("EGP " + String.format("%,.2f", total));
    }

    private void _loadWalletBreakdown() {
        _walletBreakdownRow.getChildren().clear();
        List<ClsWallet> wallets = _walletService.getByUser($currentUser.getUserID());

        String[] balanceStyles = { "text-green", "text-blue", "text-purple", "text-amber" };
        int idx = 0;

        for (ClsWallet w : wallets) {
            VBox mini = new VBox(2);
            mini.getStyleClass().add("card-mini");
            HBox.setHgrow(mini, Priority.ALWAYS);

            Label nameLabel = new Label(w.get_name());
            nameLabel.getStyleClass().add("text-muted");

            Label balLabel = new Label("EGP " + String.format("%,.0f", w.get_balance()));
            balLabel.getStyleClass().addAll("mono-xs", balanceStyles[idx % balanceStyles.length]);

            mini.getChildren().addAll(nameLabel, balLabel);
            _walletBreakdownRow.getChildren().add(mini);
            idx++;
        }
    }

    private void _loadCycleSummary() {
        Optional<ClsCycle> optCycle = _cycleService.getActiveCycle($currentUser.getUserID());

        boolean hasCycle = optCycle.isPresent();
        _cycleCard.setVisible(hasCycle);
        _cycleCard.setManaged(hasCycle);
        _noCycleCard.setVisible(!hasCycle);
        _noCycleCard.setManaged(!hasCycle);

        if (!hasCycle)
            return;

        ClsCycle cycle = optCycle.get();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM d");

        _cycleTitleLabel.setText(cycle.get_startDate().format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        _cycleDateLabel.setText(cycle.get_startDate().format(fmt) + " \u2192 " + cycle.get_endDate().format(fmt));

        double budget = cycle.get_budgetAmount();
        double spent = _cycleService.getTotalSpent(cycle.get_cycleID());
        double remaining = budget - spent;
        double pct = budget > 0 ? Math.min((spent / budget) * 100.0, 100.0) : 0;

        _cycleBudgetLabel.setText("EGP " + String.format("%,.0f", budget));
        _cycleSpentLabel.setText("EGP " + String.format("%,.0f", spent));
        _cycleRemainingLabel.setText("EGP " + String.format("%,.0f", remaining));
        _cycleProgressBar.setProgress(pct / 100.0);

        if (spent > budget) {
            _cycleProgressBar.setStyle("-fx-accent: #EF4444;");
            _cyclePercentLabel.setText(String.format("%.0f%% — over budget!", pct));
        } else {
            _cycleProgressBar.setStyle("");
            _cyclePercentLabel.setText(String.format("%.0f%% of budget used", pct));
        }
    }

    // ── Charts ────────────────────────────────────────────────────────────

    private void _loadExpenseChart(List<ClsTransaction> txns, List<ClsCategory> cats) {

        // Build category name map
        Map<Integer, ClsCategory> catMap = new HashMap<>();
        for (ClsCategory c : cats)
            catMap.put(c.get_categoryID(), c);

        // Sum expenses by category
        Map<Integer, Double> totals = new HashMap<>();
        double grandTotal = 0;
        for (ClsTransaction t : txns) {
            if (t.get_type() == EnTransactionType.EXPENSE && t.get_categoryID() > 0) {
                totals.merge(t.get_categoryID(), t.get_amount(), Double::sum);
                grandTotal += t.get_amount();
            }
        }

        if (totals.isEmpty() || grandTotal == 0) {
            _expenseChartContent.getChildren().clear();
            Label empty = new Label("No expenses yet");
            empty.getStyleClass().add("text-muted");
            _expenseChartContent.getChildren().add(empty);
            return;
        }

        // Convert to segments
        double[][] segs = new double[totals.size()][4];
        String[] labels = new String[totals.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> e : totals.entrySet()) {
            double pct = (e.getValue() / grandTotal) * 100.0;
            String hex = catMap.containsKey(e.getKey())
                    ? catMap.get(e.getKey()).get_hexColor()
                    : PALETTE[i % PALETTE.length];
            Color c = _parseHex(hex);
            segs[i] = new double[] { pct, c.getRed() * 255, c.getGreen() * 255, c.getBlue() * 255 };
            labels[i] = catMap.containsKey(e.getKey()) ? catMap.get(e.getKey()).get_name() : "Cat " + e.getKey();
            i++;
        }
        _buildDonutChart(_expenseChartContent, segs, labels);
    }

    private void _loadDepositChart(List<ClsTransaction> txns, List<ClsCategory> cats) {

        Map<Integer, ClsCategory> catMap = new HashMap<>();
        for (ClsCategory c : cats)
            catMap.put(c.get_categoryID(), c);

        Map<Integer, Double> totals = new HashMap<>();
        double grandTotal = 0;
        for (ClsTransaction t : txns) {
            if (t.get_type() == EnTransactionType.DEPOSIT && t.get_categoryID() > 0) {
                totals.merge(t.get_categoryID(), t.get_amount(), Double::sum);
                grandTotal += t.get_amount();
            }
        }

        if (totals.isEmpty() || grandTotal == 0) {
            _depositChartContent.getChildren().clear();
            Label empty = new Label("No deposits yet");
            empty.getStyleClass().add("text-muted");
            _depositChartContent.getChildren().add(empty);
            return;
        }

        double[][] segs = new double[totals.size()][4];
        String[] labels = new String[totals.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> e : totals.entrySet()) {
            double pct = (e.getValue() / grandTotal) * 100.0;
            String hex = catMap.containsKey(e.getKey())
                    ? catMap.get(e.getKey()).get_hexColor()
                    : PALETTE[i % PALETTE.length];
            Color c = _parseHex(hex);
            segs[i] = new double[] { pct, c.getRed() * 255, c.getGreen() * 255, c.getBlue() * 255 };
            labels[i] = catMap.containsKey(e.getKey()) ? catMap.get(e.getKey()).get_name() : "Cat " + e.getKey();
            i++;
        }
        _buildDonutChart(_depositChartContent, segs, labels);
    }

    private void _loadWalletChart() {
        List<ClsWallet> wallets = _walletService.getByUser($currentUser.getUserID());

        // Clamp negative balances to 0 so they don't distort the chart
        double grandTotal = wallets.stream()
                .mapToDouble(w -> Math.max(0, w.get_balance()))
                .sum();

        if (wallets.isEmpty() || grandTotal == 0) {
            _walletChartContent.getChildren().clear();
            Label empty = new Label("No wallets yet");
            empty.getStyleClass().add("text-muted");
            _walletChartContent.getChildren().add(empty);
            return;
        }

        double[][] segs = new double[wallets.size()][4];
        String[] labels = new String[wallets.size()];
        for (int i = 0; i < wallets.size(); i++) {
            ClsWallet w = wallets.get(i);
            double balance = Math.max(0, w.get_balance()); // treat negative as 0
            double pct = (balance / grandTotal) * 100.0;
            Color c = _parseHex(PALETTE[i % PALETTE.length]);
            segs[i] = new double[] { pct, c.getRed() * 255, c.getGreen() * 255, c.getBlue() * 255 };
            labels[i] = w.get_name();
        }
        _buildDonutChart(_walletChartContent, segs, labels);
    }

    // ── Donut chart builder ───────────────────────────────────────────────

    private void _buildDonutChart(HBox container, double[][] segs, String[] labels) {
        container.getChildren().clear();

        int size = 160, strokeWidth = 22;
        Canvas canvas = new Canvas(size, size);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double cx = size / 2.0, cy = size / 2.0;
        double r = cx - strokeWidth / 2.0 - 4;

        double startAngle = 90;
        for (double[] seg : segs) {
            double sweep = seg[0] / 100.0 * 360.0;
            gc.setStroke(Color.rgb((int) seg[1], (int) seg[2], (int) seg[3]));
            gc.setLineWidth(strokeWidth);
            gc.strokeArc(cx - r, cy - r, r * 2, r * 2, startAngle, -sweep, javafx.scene.shape.ArcType.OPEN);
            startAngle -= sweep;
        }

        // Centre hole
        double innerR = r - strokeWidth / 2.0 - 2;
        gc.setFill(Color.web("#080B12"));
        gc.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);

        // Centre text — biggest segment
        int topIdx = 0;
        double topPct = 0;
        for (int i = 0; i < segs.length; i++) {
            if (segs[i][0] > topPct) {
                topPct = segs[i][0];
                topIdx = i;
            }
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.web("#DCE1EE"));
        gc.setFont(Font.font("System", FontWeight.BOLD, 16));
        gc.fillText((int) topPct + "%", cx, cy + 2);
        gc.setFill(Color.web("#4A5268"));
        gc.setFont(Font.font("System", 11));
        gc.fillText(labels[topIdx], cx, cy + 17);

        container.getChildren().add(canvas);

        // Legend
        VBox legend = new VBox(8);
        legend.getStyleClass().add("bg-transparent");
        legend.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < segs.length; i++) {
            HBox row = new HBox(7);
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("bg-transparent");

            Region dot = new Region();
            dot.setPrefSize(10, 10);
            dot.setMinSize(10, 10);
            dot.getStyleClass().add("legend-dot");
            Color c = Color.rgb((int) segs[i][1], (int) segs[i][2], (int) segs[i][3]);
            dot.setStyle("-fx-background-color: " + _toHex(c) + "; -fx-background-radius: 50%;");

            Label nameLbl = new Label(labels[i]);
            nameLbl.getStyleClass().add("legend-label");
            nameLbl.setStyle("-fx-font-size: 11px;");
            HBox.setHgrow(nameLbl, Priority.ALWAYS);

            Label valLbl = new Label(String.format("%.0f%%", segs[i][0]));
            valLbl.getStyleClass().add("legend-value");
            valLbl.setStyle("-fx-font-size: 11px; -fx-font-weight: bold;");

            row.getChildren().addAll(dot, nameLbl, valLbl);
            legend.getChildren().add(row);
        }
        container.getChildren().add(legend);
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private Color _parseHex(String hex) {
        try {
            if (hex != null && hex.startsWith("#") && (hex.length() == 7 || hex.length() == 4)) {
                return Color.web(hex);
            }
        } catch (Exception ignored) {
        }
        return Color.web("#4A5268"); // fallback muted
    }

    private String _toHex(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    // ── ABaseController / IObserver ───────────────────────────────────────

    @Override
    public void refreshData() {
        if ($currentUser == null)
            return;
        _loadTotalBalance();
        _loadWalletBreakdown();
        _loadCycleSummary();
        _loadWalletChart();

        List<ClsTransaction> allTxns = _transactionService.getByUser($currentUser.getUserID());
        List<ClsCategory> allCats = _categoryService.getByUser($currentUser.getUserID());

        _loadExpenseChart(allTxns, allCats);
        _loadDepositChart(allTxns, allCats);
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        // Re-render whenever any financial data changes
        if (evenType == EnEvenType.TRANSACTION_ADDED || evenType == EnEvenType.TRANSACTION_UPDATED
                || evenType == EnEvenType.TRANSACTION_DELETED
                || evenType == EnEvenType.WALLET_ADDED || evenType == EnEvenType.WALLET_UPDATED
                || evenType == EnEvenType.WALLET_DELETED
                || evenType == EnEvenType.GOAL_ADDED || evenType == EnEvenType.GOAL_UPDATED
                || evenType == EnEvenType.GOAL_DELETED
                || evenType == EnEvenType.CYCLE_ADDED || evenType == EnEvenType.CYCLE_UPDATED
                || evenType == EnEvenType.CYCLE_DELETED
                || evenType == EnEvenType.CATEGORY_ADDED || evenType == EnEvenType.CATEGORY_UPDATED
                || evenType == EnEvenType.CATEGORY_DELETED) {
            refreshData();
        }
    }
}
