# 💸 $penderman - Smart Personal Finance & Micro-Budgeting

![Java](https://img.shields.io/badge/Java-17%2B-orange.svg?style=flat&logo=openjdk)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey)
![Framework](https://img.shields.io/badge/Framework-JavaFX-blue)
![Database](https://img.shields.io/badge/Database-SQL%20Server-red)
![Architecture](https://img.shields.io/badge/Architecture-MVC%20%7C%20DAO-success)
![Patterns](https://img.shields.io/badge/Patterns-Observer%20%7C%20Singleton-purple)

**$penderman** is a comprehensive personal finance desktop application engineered to simplify daily money management and expense tracking. Built with a reactive JavaFX interface and a robust SQL Server backend, the application features a dynamic dashboard that provides real-time visual analytics of a user's financial health. 

The system empowers users to manage multiple custom wallets, categorize transactions with personalized color-coding, and allocate funds directly to specific saving goals. With secure multi-user authentication, strict data integrity rules, and persistent UI customization, $penderman delivers a polished and reliable toolkit for building better financial habits.

> **🎥 [Watch the Full Video Demonstration Here](#)** *(Link your YouTube/LinkedIn video here once recorded!)*

---

## 🌟 Key Features

### 📊 Core Financial Tracking
* **Time-Boxed Budget Cycles:** Organize your finances into specific budget periods[cite: 1]. The system actively tracks budget consumption over your selected date range and ensures only one active budget exists per user, automatically tracking past cycles[cite: 1].
* **Multi-Wallet Management:** Create and track multiple accounts (e.g., Cash, Visa) independently, with balances derived entirely from recorded transactions[cite: 1].
* **Strict Transaction Ledger:** Log expenses and deposits with confidence. The backend enforces a mutual exclusivity (XOR) rule, ensuring a transaction targets exactly one Wallet OR one Saving Goal to maintain perfect data integrity[cite: 1].
* **Custom Categories:** Classify your transactions using user-defined labels and assign custom hex colors to them for personalized chart rendering[cite: 1].
* **Interactive Dashboard:** View real-time financial analytics, including dynamic JavaFX Pie Charts that aggregate your expenses, deposits, and wallet distributions[cite: 4].

### 🚀 Advanced & Bonus Features
* **🤖 AI-Powered Financial Assistant:** An integrated, on-demand AI chatbot that provides personalized financial advice and insights directly within the app environment[cite: 2, 4].
* **🎯 Dynamic Saving Goals:** Set specific financial milestones equipped with visual progress bars[cite: 1, 4]. Goals are funded securely through the transaction ledger and automatically update to a "Completed" status when the target is reached[cite: 1].
* **⚡ Reactive UI via Event Bus:** Utilizes a custom `AppEventBus` acting as a Publisher/Subscriber hub (Observer Pattern)[cite: 1]. When a transaction is added, the dashboard, charts, and wallet balances refresh instantaneously without manual page reloads[cite: 1, 2].
* **🎨 Dynamic Theming Engine:** A persistent Light/Dark mode toggle[cite: 1, 4]. User UI preferences are securely saved to the database[cite: 1].
* **🔒 Robust Security:** Features secure, hashed password management, robust session control, and multi-user authentication[cite: 1, 4].

---

## 🏗️ Software Architecture & Design
The system was designed strictly adhering to **SOLID Principles** and standard Software Engineering practices:
* **Separation of Concerns (SRP):** Clean MVC architecture separating JavaFX Controllers (UI logic), Business Services (core logic), and Database Access logic[cite: 1, 2].
* **Design Patterns Implemented:**
  * **DAO (Data Access Object) & Repository Pattern:** Abstracts raw SQL Server queries away from the Service Layer, utilizing a generic `IRepository<T>` interface[cite: 1, 2].
  * **Observer Pattern:** Drives the central `AppEventBus` for decoupled, reactive UI updates across all JavaFX controllers[cite: 1, 2].
  * **Singleton Pattern:** Manages central infrastructure like the `SceneManager`, `AppEventBus`, and the active `ClsDatabaseConnection` to prevent performance overhead[cite: 1, 2].
* **Dependency Inversion (DIP):** High-level Service constructors accept abstractions (e.g., `IRepository<T>`) rather than concrete database classes, keeping the business logic completely decoupled from the database implementation[cite: 1, 2].

---

## 🛠️ Build & Installation

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher.
* **Maven:** For dependency management.
* **Microsoft SQL Server:** Must be installed and running.

### Setup Instructions
1. **Clone the Repository:**
    ```bash
    git clone [https://github.com/Eng-Ahmed-Shawareb/spenderman.git](https://github.com/Eng-Ahmed-Shawareb/spenderman.git)
    cd spenderman
    
```
2. **Database Initialization:**
    * Open Azure Data Studio or SQL Server Management Studio (SSMS).
    * Execute the `.sql` scripts located in the `Database/` folder in the correct relational order to build the schema.
3. **Configure Connection:**
    * Update the connection string inside `src/main/java/com/spenderman/DAO/Singleton/ClsDatabaseConnection.java` with your local SQL Server credentials[cite: 1].
4. **Compile and Run:**
    ```bash
    mvn clean javafx:run
    
```

---

## 📂 Project Structure

    ├── Database/                   # SQL Scripts for schema creation
    ├── Project Design/             # UMLs, ERDs, Mockups, and Documentation
    ├── pom.xml                     # Maven configuration
    └── src/main/
        ├── java/com/spenderman/
        │   ├── DAO/                # Data Access Objects & Repository Interfaces
        │   ├── model/              # Domain Entities (User, Wallet, Transaction, Cycle, etc.)
        │   ├── Observer/           # Custom AppEventBus implementation
        │   ├── service/            # Core business logic & AI integration
        │   └── ui/                 # JavaFX Controllers & Scene Management
        └── resources/              
            └── com/spenderman/ui/  # FXML Views and CSS Styling (Dark/Light modes)

---

## 📜 Credits & Acknowledgments
* **Lead Developer / Project Manager:** Ahmed Shawareb[cite: 1]
* **Development Team:** Ahmed Salah, Mohamed Ayser, Malak, Radwa, Mariam, Hesham[cite: 1].
* **Academic Context:** Developed as part of the CS251 - Introduction to Software Engineering course at the Faculty of Computers and Artificial Intelligence, Cairo University[cite: 1].

## 📬 Contact
* **Email:** eng.ahmedshawareb@gmail.com
* **GitHub:** [Eng-Ahmed-Shawareb](https://github.com/Eng-Ahmed-Shawareb)
