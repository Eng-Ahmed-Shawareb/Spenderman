# 💸 $penderman - Smart Personal Finance & Micro-Budgeting

![Java](https://img.shields.io/badge/Java-17%2B-orange.svg?style=flat&logo=openjdk)
![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey)
![Framework](https://img.shields.io/badge/Framework-JavaFX-blue)
![Database](https://img.shields.io/badge/Database-SQL%20Server-red)
![Architecture|148](https://img.shields.io/badge/Architecture-MVC%20%7C%20DAO-success)
![Patterns](https://img.shields.io/badge/Patterns-Observer%20%7C%20Singleton-purple)

**$penderman** is a comprehensive personal finance desktop application engineered to simplify daily money management and expense tracking. Built with a reactive JavaFX interface and a robust SQL Server backend, the application features a dynamic dashboard that provides real-time visual analytics of a user's financial health. 

The system empowers users to manage multiple custom wallets, categorize transactions with personalized color-coding, and allocate funds directly to specific saving goals. With secure multi-user authentication, strict data integrity rules, and persistent UI customization, $penderman delivers a polished and reliable toolkit for building better financial habits.

> **🎥 [Watch the Full Video Demonstration Here](https://www.youtube.com/watch?v=disy_VrYJek)** 

---

## 🌟 Key Features

### 📊 Core Financial Tracking
* **Time-Boxed Budget Cycles:** Organize your finances into specific budget periods. The system actively tracks budget consumption over your selected date range and ensures only one active budget exists per user, automatically tracking past cycles.
* **Multi-Wallet Management:** Create and track multiple accounts (e.g., Cash, Visa) independently, with balances derived entirely from recorded transactions.
* **Strict Transaction Ledger:** Log expenses and deposits with confidence. The backend enforces a mutual exclusivity (XOR) rule, ensuring a transaction targets exactly one Wallet OR one Saving Goal to maintain perfect data integrity.
* **Custom Categories:** Classify your transactions using user-defined labels and assign custom hex colors to them for personalized chart rendering.
* **Interactive Dashboard:** View real-time financial analytics, including dynamic JavaFX Pie Charts that aggregate your expenses, deposits, and wallet distributions.

### 🚀 Advanced & Bonus Features
* **🤖 AI-Powered Financial Assistant:** An integrated, on-demand AI chatbot that provides personalized financial advice and insights directly within the app environment.
* **🎯 Dynamic Saving Goals:** Set specific financial milestones equipped with visual progress bars. Goals are funded securely through the transaction ledger and automatically update to a "Completed" status when the target is reached.
* **⚡ Reactive UI via Event Bus:** Utilizes a custom `AppEventBus` acting as a Publisher/Subscriber hub (Observer Pattern). When a transaction is added, the dashboard, charts, and wallet balances refresh instantaneously without manual page reloads.
* **🎨 Dynamic Theming Engine:** A persistent Light/Dark mode toggle. User UI preferences are securely saved to the database.
* **🔒 Robust Security:** Features secure, hashed password management, robust session control, and multi-user authentication.

---

## 🏗️ Software Architecture & Design
The system was designed strictly adhering to **SOLID Principles** and standard Software Engineering practices:
* **Separation of Concerns (SRP):** Clean MVC architecture separating JavaFX Controllers (UI logic), Business Services (core logic), and Database Access logic.
* **Design Patterns Implemented:**
  * **DAO (Data Access Object) & Repository Pattern:** Abstracts raw SQL Server queries away from the Service Layer, utilizing a generic `IRepository<T>` interface.
  * **Observer Pattern:** Drives the central `AppEventBus` for decoupled, reactive UI updates across all JavaFX controllers.
  * **Singleton Pattern:** Manages central infrastructure like the `SceneManager`, `AppEventBus`, and the active `ClsDatabaseConnection` to prevent performance overhead.
* **Dependency Inversion (DIP):** High-level Service constructors accept abstractions (e.g., `IRepository<T>`) rather than concrete database classes, keeping the business logic completely decoupled from the database implementation.

---

## 🛠️ Build & Installation

### Prerequisites
* **Java Development Kit (JDK):** Version 25 or higher.
* **Maven:** For dependency management.

### Setup Instructions
1. **Clone the Repository:**
    ```bash
    git clone https://github.com/Eng-Ahmed-Shawareb/spenderman.git 
    cd spenderman
    ```
2. **Compile and Run:**
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
* **Lead Developer / Project Manager:** Ahmed Shawareb.
* **Development Team:** Ahmed Salah, Mohamed Ayser.
* **Academic Context:** Developed as part of the CS251 - Introduction to Software Engineering course at the Faculty of Computers and Artificial Intelligence, Cairo University.

## 📬 Contact
### Ahmed Shawareb
* **Email:** eng.ahmedshawareb@gmail.com
* **GitHub:** [Eng-Ahmed-Shawareb](https://github.com/Eng-Ahmed-Shawareb)
* **LinkedIn:** [Ahmed Shawareb](https://www.linkedin.com/in/ahmed-shawareb-82183133b/)
### Ahmed Salah
* **Email:** adh739184@gmail.com
* **GitHub:** [AhmedMo206](https://github.com/AhmedMo206)
* **LinkedIn:** [Ahmed Salah](https://www.linkedin.com/in/ahmed-mohamed-bb3598343/)
### Mohamed Ayser
* **Email:** aysermohamedal@gmail.com
* **GitHub:** [MohamedAyser1](https://github.com/MohamedAyser1)
* **LinkedIn:** [Mohamed Ayser](https://www.linkedin.com/in/mohamed-ayser-312718337/)
