package com.spenderman.ui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.spenderman.ui.controller.BaseController;
import com.spenderman.ui.controller.AppShellController;
import com.spenderman.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton scene manager for handling screen navigation and state.
 * Loads FXML views, applies global CSS stylesheet, and manages the app shell.
 *
 * UML: SceneManager (Singleton)
 *   - switchTo(fxmlPath) : void
 *   - showDialog(fxmlPath) : void
 *   - getController(key) : BaseController
 */
public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private Scene mainScene;
    private Map<String, BaseController> controllerCache = new HashMap<>();
    private User currentUser;
    private AppShellController shellController;

    // ═══ PATHS — must match your exact resources directory structure ═══
    private static final String VIEWS_BASE = "/com/spenderman/ui/view/";
    private static final String CSS_PATH   = "/com/spenderman/ui/style/style.css";

    // Screen ID to FXML filename mapping
    private static final Map<String, String> SCREEN_MAP = Map.of(
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
    private static final Set<String> SHELL_SCREENS = Set.of(
            "dashboard", "transactions", "wallets", "categories", "cycles", "goals", "settings"
    );

    private SceneManager() {}

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void initialize(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Switch to a screen by its ID.
     * Auth screens (login, signup) are loaded as full-page views.
     * All other screens are loaded inside the AppShell sidebar layout.
     */
    public void switchTo(String screenId) {
        try {
            if (SHELL_SCREENS.contains(screenId)) {
                switchToShellScreen(screenId);
            } else {
                switchToStandaloneScreen(screenId);
            }
        } catch (IOException e) {
            System.err.println("❌ Failed to load screen: " + screenId);
            e.printStackTrace();
        }
    }

    /**
     * Load a standalone screen (login, signup) — no sidebar.
     */
    private void switchToStandaloneScreen(String screenId) throws IOException {
        String fxmlFile = SCREEN_MAP.get(screenId);
        if (fxmlFile == null) {
            System.err.println("❌ Unknown screen ID: " + screenId);
            return;
        }

        String fullPath = VIEWS_BASE + fxmlFile;
        URL fxmlUrl = getClass().getResource(fullPath);
        if (fxmlUrl == null) {
            System.err.println("❌ FXML file not found at: " + fullPath);
            System.err.println("   Verify this file exists in src/main/resources" + fullPath);
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        BaseController controller = loader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
            controller.setCurrentUser(currentUser);
            controllerCache.put(screenId, controller);
        }

        if (mainScene == null) {
            mainScene = new Scene(root, 1000, 700);
            applyCss(mainScene);
            primaryStage.setScene(mainScene);
        } else {
            mainScene.setRoot(root);
        }

        // Clear shell reference when going to standalone screens
        shellController = null;
    }

    /**
     * Load a screen inside the AppShell (sidebar + content area).
     */
    private void switchToShellScreen(String screenId) throws IOException {
        // Load the shell if not already loaded
        if (shellController == null) {
            String shellPath = VIEWS_BASE + "AppShell.fxml";
            URL shellUrl = getClass().getResource(shellPath);
            if (shellUrl == null) {
                System.err.println("❌ AppShell.fxml not found at: " + shellPath);
                return;
            }

            FXMLLoader shellLoader = new FXMLLoader(shellUrl);
            Parent shellRoot = shellLoader.load();
            shellController = shellLoader.getController();
            shellController.setSceneManager(this);
            shellController.setCurrentUser(currentUser);

            if (mainScene == null) {
                mainScene = new Scene(shellRoot, 1000, 700);
                applyCss(mainScene);
                primaryStage.setScene(mainScene);
            } else {
                mainScene.setRoot(shellRoot);
            }
        }

        // Now load the content screen into the shell's content area
        String fxmlFile = SCREEN_MAP.get(screenId);
        String fullPath = VIEWS_BASE + fxmlFile;
        URL contentUrl = getClass().getResource(fullPath);
        if (contentUrl == null) {
            System.err.println("❌ FXML not found at: " + fullPath);
            return;
        }

        FXMLLoader contentLoader = new FXMLLoader(contentUrl);
        Parent contentRoot = contentLoader.load();

        BaseController controller = contentLoader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
            controller.setCurrentUser(currentUser);
            controllerCache.put(screenId, controller);
        }

        // Set the content into the shell
        shellController.setContent(contentRoot);
        shellController.setActiveNav(screenId);
    }

    /**
     * Apply CSS stylesheet to the scene. Logs a warning if the file is not found.
     */
    private void applyCss(Scene scene) {
        URL cssUrl = getClass().getResource(CSS_PATH);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
            System.out.println("✅ CSS loaded: " + CSS_PATH);
        } else {
            System.err.println("⚠️ CSS not found at: " + CSS_PATH);
            System.err.println("   Verify this file exists in src/main/resources" + CSS_PATH);
        }
    }

    public void logout() {
        currentUser = null;
        controllerCache.clear();
        shellController = null;
        switchTo("login");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BaseController getController(String key) {
        return controllerCache.get(key);
    }
}