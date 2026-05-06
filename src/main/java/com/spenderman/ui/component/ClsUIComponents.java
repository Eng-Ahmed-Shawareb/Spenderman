package com.spenderman.ui.component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class representing ClsUIComponents.
 *
 * @author Spenderman Team
 * @version 1.0
 */
public class ClsUIComponents {

    public static final String BG = "#080B12";

    public static final String SURF = "#0F1319";

    public static final String SURF2 = "#161B26";

    public static final String SURF3 = "#1D2333";

    public static final String BORDER = "#252D40";

    public static final String GREEN = "#22C97A";

    public static final String PURPLE = "#8875F5";

    public static final String BLUE = "#4B9EF8";

    public static final String AMBER = "#F5A623";

    public static final String RED = "#F56565";

    public static final String PINK = "#F472B6";

    public static final String TEXT = "#DCE1EE";

    public static final String SUB = "#8490A8";

    public static final String MUTED = "#4A5268";

    /**
     * Method to label.
     *
     * @param text the text
     * @return the Label
     */
    public static Label label(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.findByWeight(11), 12));
        label.setTextFill(javafx.scene.paint.Color.web(TEXT));
        return label;
    }

    /**
     * Method to labelMuted.
     *
     * @param text the text
     * @return the Label
     */
    public static Label labelMuted(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.findByWeight(10), 10));
        label.setTextFill(javafx.scene.paint.Color.web(MUTED));
        return label;
    }

    /**
     * Method to heading.
     *
     * @param text the text
     * @return the Label
     */
    public static Label heading(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.BOLD, 14));
        label.setTextFill(javafx.scene.paint.Color.web(TEXT));
        return label;
    }

    /**
     * Method to fieldLabel.
     *
     * @param text the text
     * @return the Label
     */
    public static Label fieldLabel(String text) {
        Label label = new Label(text.toUpperCase());
        label.setFont(Font.font("System", FontWeight.BOLD, 9));
        label.setTextFill(javafx.scene.paint.Color.web(SUB));
        label.setStyle("-fx-text-fill: " + SUB + "; -fx-font-size: 9; -fx-font-weight: bold;");
        return label;
    }

    /**
     * Method to textField.
     *
     * @param placeholder the placeholder
     * @return the TextField
     */
    public static TextField textField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefHeight(32);
        field.setStyle("-fx-background-color: " + SURF3 + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-text-fill: " + TEXT + ";" + "-fx-control-inner-background: " + SURF3 + ";" + "-fx-prompt-text-fill: " + MUTED + ";" + "-fx-padding: 7, 10, 7, 10;" + "-fx-font-size: 11;" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;");
        return field;
    }

    /**
     * Method to passwordField.
     *
     * @param placeholder the placeholder
     * @return the PasswordField
     */
    public static PasswordField passwordField(String placeholder) {
        PasswordField field = new PasswordField();
        field.setPromptText(placeholder);
        field.setPrefHeight(32);
        field.setStyle("-fx-background-color: " + SURF3 + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-text-fill: " + TEXT + ";" + "-fx-control-inner-background: " + SURF3 + ";" + "-fx-prompt-text-fill: " + MUTED + ";" + "-fx-padding: 7, 10, 7, 10;" + "-fx-font-size: 11;" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;");
        return field;
    }

    /**
     * Method to comboBox.
     *
     * @param items the items
     * @return the ComboBox<String>
     */
    public static ComboBox<String> comboBox(String... items) {
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(items);
        combo.setPrefHeight(32);
        combo.setStyle("-fx-background-color: " + SURF3 + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-text-fill: " + TEXT + ";" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;" + "-fx-font-size: 11;");
        return combo;
    }

    /**
     * Method to textArea.
     *
     * @param placeholder the placeholder
     * @param height the height
     * @return the TextArea
     */
    public static TextArea textArea(String placeholder, int height) {
        TextArea area = new TextArea();
        area.setWrapText(true);
        area.setPrefHeight(height);
        area.setPromptText(placeholder);
        area.setStyle("-fx-background-color: " + SURF3 + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-text-fill: " + TEXT + ";" + "-fx-control-inner-background: " + SURF3 + ";" + "-fx-padding: 7, 10, 7, 10;" + "-fx-font-size: 11;" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;");
        return area;
    }

    /**
     * Method to button.
     *
     * @param text the text
     * @param color the color
     * @return the Button
     */
    public static Button button(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + ";" + "-fx-text-fill: #000;" + "-fx-border-width: 0;" + "-fx-padding: 7, 14, 7, 14;" + "-fx-font-size: 11;" + "-fx-font-weight: bold;" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;" + "-fx-cursor: hand;");
        return btn;
    }

    /**
     * Method to primaryButton.
     *
     * @param text the text
     * @return the Button
     */
    public static Button primaryButton(String text) {
        return button(text, GREEN);
    }

    /**
     * Method to outlineButton.
     *
     * @param text the text
     * @return the Button
     */
    public static Button outlineButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: " + SUB + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-padding: 7, 14, 7, 14;" + "-fx-font-size: 11;" + "-fx-font-weight: bold;" + "-fx-border-radius: 6;" + "-fx-cursor: hand;");
        return btn;
    }

    /**
     * Method to dangerButton.
     *
     * @param text the text
     * @return the Button
     */
    public static Button dangerButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: " + RED + ";" + "-fx-border-color: " + RED + ";" + "-fx-border-width: 1;" + "-fx-padding: 4, 10, 4, 10;" + "-fx-font-size: 10;" + "-fx-font-weight: bold;" + "-fx-border-radius: 6;" + "-fx-cursor: hand;");
        return btn;
    }

    /**
     * Method to smallButton.
     *
     * @param text the text
     * @return the Button
     */
    public static Button smallButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + GREEN + ";" + "-fx-text-fill: #000;" + "-fx-border-width: 0;" + "-fx-padding: 4, 10, 4, 10;" + "-fx-font-size: 10;" + "-fx-font-weight: bold;" + "-fx-border-radius: 6;" + "-fx-background-radius: 6;" + "-fx-cursor: hand;");
        return btn;
    }

    /**
     * Method to card.
     *
     * @param padding the padding
     * @return the VBox
     */
    public static VBox card(double padding) {
        VBox card = new VBox();
        card.setPadding(new Insets(padding));
        card.setStyle("-fx-background-color: " + SURF + ";" + "-fx-border-color: " + BORDER + ";" + "-fx-border-width: 1;" + "-fx-border-radius: 8;" + "-fx-background-radius: 8;");
        return card;
    }

    /**
     * Method to card.
     *
     * @return the VBox
     */
    public static VBox card() {
        return card(12);
    }

    /**
     * Method to row.
     *
     * @return the HBox
     */
    public static HBox row() {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(12);
        return row;
    }

    /**
     * Method to column.
     *
     * @param spacing the spacing
     * @return the VBox
     */
    public static VBox column(double spacing) {
        VBox col = new VBox(spacing);
        col.setStyle("-fx-background-color: transparent;");
        return col;
    }

    /**
     * Method to badge.
     *
     * @param text the text
     * @param color the color
     * @return the Label
     */
    public static Label badge(String text, String color) {
        Label badge = new Label(text);
        badge.setStyle("-fx-background-color: " + color + "22;" + "-fx-text-fill: " + color + ";" + "-fx-padding: 2, 7, 2, 7;" + "-fx-font-size: 10;" + "-fx-font-weight: bold;" + "-fx-border-radius: 4;" + "-fx-background-radius: 4;");
        return badge;
    }

    /**
     * Method to badgeGreen.
     *
     * @param text the text
     * @return the Label
     */
    public static Label badgeGreen(String text) {
        return badge(text, GREEN);
    }

    /**
     * Method to badgeRed.
     *
     * @param text the text
     * @return the Label
     */
    public static Label badgeRed(String text) {
        return badge(text, RED);
    }

    /**
     * Method to badgeAmber.
     *
     * @param text the text
     * @return the Label
     */
    public static Label badgeAmber(String text) {
        return badge(text, AMBER);
    }

    /**
     * Method to progressBar.
     *
     * @param percent the percent
     * @return the ProgressBar
     */
    public static ProgressBar progressBar(double percent) {
        ProgressBar bar = new ProgressBar(percent / 100.0);
        bar.setPrefHeight(6);
        bar.setStyle("-fx-accent: " + GREEN + ";" + "-fx-control-inner-background: " + SURF3 + ";");
        return bar;
    }

    /**
     * Method to progressBar.
     *
     * @param percent the percent
     * @param color the color
     * @return the ProgressBar
     */
    public static ProgressBar progressBar(double percent, String color) {
        ProgressBar bar = new ProgressBar(Math.min(percent / 100.0, 1.0));
        bar.setPrefHeight(6);
        bar.setStyle("-fx-accent: " + color + ";" + "-fx-control-inner-background: " + SURF3 + ";");
        return bar;
    }

    /**
     * Method to divider.
     *
     * @return the Separator
     */
    public static Separator divider() {
        Separator sep = new Separator();
        sep.setStyle("-fx-border-color: " + BORDER + "; -fx-padding: 10 0 10 0;");
        return sep;
    }
}
