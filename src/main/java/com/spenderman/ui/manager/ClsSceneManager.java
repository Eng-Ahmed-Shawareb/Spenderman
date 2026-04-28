package com.spenderman.ui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.spenderman.ui.controller.ABaseController;
import com.spenderman.ui.controller.ClsAppShellController;
import com.spenderman.model.ClsUser;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton scene manager for handling screen navigation and state.
 * Loads FXML views, applies global CSS stylesheet, and manages the app shell.
 *
 * UML: ClsSceneManager (Singleton)
 *   - switchTo(fxmlPath) : void
 *   - showDialog(fxmlPath) : void
 *   - getController(key) : ABaseController
 */
public class ClsSceneManager {
    private static ClsSceneManager _instance;
    private Stage _primaryStage;
    private Scene _mainScene;
    private Map<String, ABaseController> _controllerCache = new HashMap<>();
    private ClsUser _currentUser;
    private ClsAppShellController _shellController;

    // ═══ PATHS — must match your exact resources directory structure ═══
    private static final String _VIEWS_BASE = "/com/spenderman/ui/view/";
    private static final String _CSS_PATH   = "/com/spenderman/ui/style/style.css";

    // Screen ID to FXML filename mapping
    private static final Map<String, String> _SCREEN_MAP = Map.of(
            "login",        "LoginView.fxml",
            "signup",       "SignUpView.fxml",
            "dashboard",    "DashboardView.fxml",
            "transactions", "TransactionView.fxml",
            "wallets",      "WalletView.fxml",
            "categories",   "CategoryView.fxml",
            "cycles",       "CycleView.fxml",
            "goals",        "GoalView.fxml",
            "settings",     "SettingsView.fxml"
    );

    // Screens that live inside the AppShell (not standalone)
    private static final Set<String> _SHELL_SCREENS = Set.of(
            "dashboard", "transactions", "wallets", "categories", "cycles", "goals", "settings"
    );

    private ClsSceneManager() {}

    public static ClsSceneManager getInstance() {
        if (_instance == null) {
            _instance = new ClsSceneManager();
        }
        return _instance;
    }

    public void initialize(Stage stage) {
        this._primaryStage = stage;
    }

    /**
     * Switch to a screen by its ID.
     * Auth screens (login, signup) are loaded as full-page views.
     * All other screens are loaded inside the AppShell sidebar layout.
     */
    public void switchTo(String screenId) {
        try {
            if (_SHELL_SCREENS.contains(screenId)) {
                _switchToShellScreen(screenId);
            } else {
                _switchToStandaloneScreen(screenId);
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to load screen: " + screenId);
            e.printStackTrace();
        }
    }

    /**
     * Load a standalone screen (login, signup) — no sidebar.
     */
    private void _switchToStandaloneScreen(String screenId) throws IOException {
        String fxmlFile = _SCREEN_MAP.get(screenId);
        if (fxmlFile == null) {
            System.err.println("❌ Unknown screen ID: " + screenId);
            return;
        }

        String fullPath = _VIEWS_BASE + fxmlFile;
        URL fxmlUrl = getClass().getResource(fullPath);
        if (fxmlUrl == null) {
            System.err.println("❌ FXML file not found at: " + fullPath);
            System.err.println("   Verify this file exists in src/main/resources" + fullPath);
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent $root = loader.load();

        ABaseController controller = loader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
            controller.setCurrentUser(_currentUser);
            _controllerCache.put(screenId, controller);
        }

        if (_mainScene == null) {
            _mainScene = new Scene($root, 1000, 700);
            _applyCss(_mainScene);
            _primaryStage.setScene(_mainScene);
        } else {
            _mainScene.setRoot($root);
        }

        // Clear shell reference when going to standalone screens
        _shellController = null;
    }

    /**
     * Load a screen inside the AppShell (sidebar + content area).
     */
    private void _switchToShellScreen(String screenId) throws IOException {
        // Load the shell if not already loaded
        if (_shellController == null) {
            String shellPath = _VIEWS_BASE + "AppShell.fxml";
            URL shellUrl = getClass().getResource(shellPath);
            if (shellUrl == null) {
                System.err.println("❌ AppShell.fxml not found at: " + shellPath);
                return;
            }

            FXMLLoader shellLoader = new FXMLLoader(shellUrl);
            Parent shellRoot = shellLoader.load();
            _shellController = shellLoader.getController();
            _shellController.setSceneManager(this);
            _shellController.setCurrentUser(_currentUser);

            if (_mainScene == null) {
                _mainScene = new Scene(shellRoot, 1000, 700);
                _applyCss(_mainScene);
                _primaryStage.setScene(_mainScene);
            } else {
                _mainScene.setRoot(shellRoot);
            }
        }

        // Now load the content screen into the shell's content area
        String fxmlFile = _SCREEN_MAP.get(screenId);
        String fullPath = _VIEWS_BASE + fxmlFile;
        URL contentUrl = getClass().getResource(fullPath);
        if (contentUrl == null) {
            System.err.println("❌ FXML not found at: " + fullPath);
            return;
        }

        FXMLLoader contentLoader = new FXMLLoader(contentUrl);
        Parent contentRoot = contentLoader.load();

        ABaseController controller = contentLoader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
            controller.setCurrentUser(_currentUser);
            _controllerCache.put(screenId, controller);
        }

        // Set the content into the shell
        _shellController.setContent(contentRoot);
        _shellController.setActiveNav(screenId);
    }

    /**
     * Apply CSS stylesheet to the scene. Logs a warning if the file is not found.
     */
    private void _applyCss(Scene scene) {
        URL cssUrl = getClass().getResource(_CSS_PATH);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("✅ CSS loaded: " + _CSS_PATH);
        } else {
            System.err.println("⚠️ CSS not found at: " + _CSS_PATH);
            System.err.println("   Verify this file exists in src/main/resources" + _CSS_PATH);
        }
    }

    public void logout() {
        _currentUser = null;
        _controllerCache.clear();
        _shellController = null;
        switchTo("login");
    }

    public void setCurrentUser(ClsUser user) {
        this._currentUser = user;
    }

    public ClsUser getCurrentUser() {
        return _currentUser;
    }

    public Stage getPrimaryStage() {
        return _primaryStage;
    }

    public ABaseController getController(String key) {
        return _controllerCache.get(key);
    }
}