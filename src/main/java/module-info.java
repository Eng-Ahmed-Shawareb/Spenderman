module com.spenderman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires java.net.http;

    // Allow JavaFX to reflectively access controllers and the main app
    opens com.spenderman to javafx.fxml;
    opens com.spenderman.ui.controller to javafx.fxml;
    opens com.spenderman.ui.manager to javafx.fxml;

    exports com.spenderman;
    exports com.spenderman.model;
    exports com.spenderman.ui.controller;
    exports com.spenderman.ui.manager;
    exports com.spenderman.ui.component;
}