package com.spenderman.ui.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

import com.spenderman.Observer.interfaceClass.IObserver;
import com.spenderman.Observer.EvenEnum.EnEvenType;
import com.spenderman.Observer.Singleton.ClsAppEventBus;

/**
 * Singleton scene manager for handling screen navigation and state.
 */
public class ClsSceneManager implements IObserver {
    private static ClsSceneManager _instance;
    private Stage _primaryStage;
    private Scene _mainScene;
    private Map<String, ABaseController> _controllerCache = new HashMap<>();
    private Map<String, Parent> _contentCache = new HashMap<>();
    private ClsUser _currentUser;
    private ClsAppShellController _shellController;
    private boolean _isDarkTheme = true;

    // ═══ PATHS ═══
    private static final String _VIEWS_BASE = "/com/spenderman/ui/view/";
    private static final String _CSS_PATH = "/com/spenderman/ui/style/style.css";

    private static final Map<String, String> _SCREEN_MAP = Map.of(
            "login", "LoginView.fxml",
            "signup", "SignUpView.fxml",
            "dashboard", "DashboardView.fxml",
            "transactions", "TransactionView.fxml",
            "wallets", "WalletView.fxml",
            "categories", "CategoryView.fxml",
            "cycles", "CycleView.fxml",
            "goals", "GoalView.fxml",
            "settings", "SettingsView.fxml",
            "ChatView", "ChatView.fxml" // <--- السطر الجديد
    );

    private static final Set<String> _SHELL_SCREENS = Set.of(
            "dashboard", "transactions", "wallets", "categories", "cycles", "goals", "settings", "ChatView");

    private ClsSceneManager() {
        ClsAppEventBus.getInstance().addObserver(this);
    }

    public static ClsSceneManager getInstance() {
        if (_instance == null) {
            _instance = new ClsSceneManager();
        }
        return _instance;
    }

    public void initialize(Stage stage) {
        this._primaryStage = stage;
    }

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
            _applyThemeToRoot();
            _primaryStage.setScene(_mainScene);
        } else {
            _mainScene.setRoot($root);
            _applyThemeToRoot();
        }

        _shellController = null;
    }

    private void _switchToShellScreen(String screenId) throws IOException {
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
                _applyThemeToRoot();
                _primaryStage.setScene(_mainScene);
            } else {
                _mainScene.setRoot(shellRoot);
                _applyThemeToRoot();
            }
        }

        if (!_controllerCache.containsKey(screenId)) {
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
                _contentCache.put(screenId, contentRoot);
            }
        }

        _shellController.setContent(_contentCache.get(screenId));
        _shellController.setActiveNav(screenId);

        ABaseController cachedController = _controllerCache.get(screenId);
        if (cachedController != null) {
            cachedController.refreshData();
        }
    }

    private void _applyCss(Scene scene) {
        URL cssUrl = getClass().getResource(_CSS_PATH);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
    }

    @Override
    public void update(EnEvenType evenType, Object data) {
        if (evenType == EnEvenType.THEME_CHANGED && data instanceof Boolean) {
            _isDarkTheme = (Boolean) data;
            _applyThemeToRoot();
        }
    }

    private void _applyThemeToRoot() {
        if (_mainScene != null && _mainScene.getRoot() != null) {
            Parent root = _mainScene.getRoot();
            String iconPath = "/utils/darkmodeicon.png";

            // Update window icon if stage is available
            if (_primaryStage != null) {
                _primaryStage.getIcons().clear();
                _primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
            }

            // Update sidebar logo if shell is active
            if (_shellController != null && _shellController.getSidebarLogo() != null) {
                _shellController.getSidebarLogo().setImage(new Image(getClass().getResourceAsStream(iconPath)));
            }

            if (_isDarkTheme) {
                root.getStyleClass().remove("light-theme");
            } else if (!root.getStyleClass().contains("light-theme")) {
                root.getStyleClass().add("light-theme");
            }
        }
    }

    public void logout() {
        _currentUser = null;
        _controllerCache.clear();
        _contentCache.clear();
        _shellController = null;

        // 1. Wipe all controllers from the event bus (frees memory & stops ghost
        // updates)
        ClsAppEventBus.getInstance().clearAllObservers();

        // 2. Re-register SceneManager so it can still handle THEME_CHANGED events
        ClsAppEventBus.getInstance().addObserver(this);

        // 3. Route back to login screen
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