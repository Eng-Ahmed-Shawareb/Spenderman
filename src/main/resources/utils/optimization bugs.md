
## Bug #1 — FXML Reloaded From Disk On Every Navigation

**File:** `ClsSceneManager.java` — the `_switchToShellScreen()` method

```java
private void _switchToShellScreen(String screenId) throws IOException {
    // ... shell setup ...

    // ❌ THIS RUNS EVERY TIME YOU CLICK A NAV BUTTON
    FXMLLoader contentLoader = new FXMLLoader(contentUrl);
    Parent contentRoot = contentLoader.load();    // reads file from disk
    ABaseController controller = contentLoader.getController();
    // ...
    _controllerCache.put(screenId, controller);   // stored in cache...
    _shellController.setContent(contentRoot);     // ...but never reused!
}
```

The `_controllerCache` map exists but **is never checked before loading**. Every time you click "Transactions" or "Wallets" in the sidebar, the FXML file is parsed from disk from scratch, a brand new controller is created, and `refreshData()` is called again — firing all those DB queries again. The cache is completely useless as written.

The fix — check the cache first:

```java
// ✅ CORRECT
private void _switchToShellScreen(String screenId) throws IOException {
    // ... shell setup (only runs once) ...

    // Check cache first — only load if not seen before
    if (!_controllerCache.containsKey(screenId)) {
        String fxmlFile  = _SCREEN_MAP.get(screenId);
        URL contentUrl   = getClass().getResource(_VIEWS_BASE + fxmlFile);
        FXMLLoader loader = new FXMLLoader(contentUrl);
        Parent contentRoot = loader.load();

        ABaseController controller = loader.getController();
        if (controller != null) {
            controller.setSceneManager(this);
            controller.setCurrentUser(_currentUser);
            _controllerCache.put(screenId, controller);
            // Store the built UI node too, not just the controller
            _contentCache.put(screenId, contentRoot); // need a second Map<String, Parent>
        }
    }

    // Reuse what's already built
    _shellController.setContent(_contentCache.get(screenId));
    _shellController.setActiveNav(screenId);

    // Only refresh data, don't rebuild the whole UI
    _controllerCache.get(screenId).refreshData();
}
```

---

## Bug #2 — The Dashboard Makes Redundant Duplicate DB Calls

**File:** `ClsDashboardController.java` — `_loadExpenseChart()` and `_loadDepositChart()`

```java
private void _loadExpenseChart() {
    // ❌ DB call #1: fetches ALL user transactions
    List<ClsTransaction> txns = _transactionService.getByUser($currentUser.getUserID());
    // ❌ DB call #2: fetches ALL categories
    List<ClsCategory> cats = _categoryService.getByUser($currentUser.getUserID());
    // ... filter for EXPENSE
}

private void _loadDepositChart() {
    // ❌ DB call #3: fetches ALL user transactions AGAIN — exact same data
    List<ClsTransaction> txns = _transactionService.getByUser($currentUser.getUserID());
    // ❌ DB call #4: fetches ALL categories AGAIN — exact same data
    List<ClsCategory> cats = _categoryService.getByUser($currentUser.getUserID());
    // ... filter for DEPOSIT
}
```

Both chart methods call `getByUser()` for transactions and categories independently. That's **4 remote round trips** for data that could be fetched in **2**. And in `_loadCycleSummary()`, there's another problem in `CycleService`:

**File:** `ClsCycleService.java`

```java
public double getTotalSpent(int cycleID) {
    // ❌ Fetches the cycle by ID — goes to DB
    Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
    // ...
}

public double getSpentPercentage(int cycleID) {
    // ❌ Fetches the cycle by ID AGAIN
    Optional<ClsCycle> optionalCycle = _cycleDAO.findByID(cycleID);
    // ...then calls getTotalSpent() which fetches it a THIRD time
    double spent = getTotalSpent(cycleID);
}
```

The fix — fetch once, pass the data around:

```java
// ✅ CORRECT — in refreshData(), fetch shared data once
private void _loadCharts() {
    // One trip for transactions, one for categories
    List<ClsTransaction> allTxns = _transactionService.getByUser($currentUser.getUserID());
    List<ClsCategory>    allCats = _categoryService.getByUser($currentUser.getUserID());

    // Pass the pre-fetched lists to both chart methods
    _buildExpenseChart(allTxns, allCats);
    _buildDepositChart(allTxns, allCats);
}
```
